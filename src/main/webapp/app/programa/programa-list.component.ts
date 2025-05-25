import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ProgramaService } from 'app/programa/programa.service';
import { ProgramaDTO } from 'app/programa/programa.model';


@Component({
  selector: 'app-programa-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './programa-list.component.html'})
export class ProgramaListComponent implements OnInit, OnDestroy {

  programaService = inject(ProgramaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  programas?: ProgramaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@programa.delete.success:Programa was removed successfully.`,
      'programa.configuracionCosto.programa.referenced': $localize`:@@programa.configuracionCosto.programa.referenced:This entity is still referenced by Configuracion Costo ${details?.id} via field Programa.`,
      'programa.grupo.programa.referenced': $localize`:@@programa.grupo.programa.referenced:This entity is still referenced by Grupo ${details?.id} via field Programa.`,
      'programa.planEstudio.programa.referenced': $localize`:@@programa.planEstudio.programa.referenced:This entity is still referenced by Plan Estudio ${details?.id} via field Programa.`,
      'programa.version.programa.referenced': $localize`:@@programa.version.programa.referenced:This entity is still referenced by Version ${details?.id} via field Programa.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.programaService.getAllProgramas()
        .subscribe({
          next: (data) => this.programas = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPrograma: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.programaService.deletePrograma(idPrograma)
        .subscribe({
          next: () => this.router.navigate(['/programas'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/programas'], {
                state: {
                  msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                }
              });
              return;
            }
            this.errorHandler.handleServerError(error.error)
          }
        });
  }

}
