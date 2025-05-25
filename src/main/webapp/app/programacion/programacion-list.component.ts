import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ProgramacionService } from 'app/programacion/programacion.service';
import { ProgramacionDTO } from 'app/programacion/programacion.model';


@Component({
  selector: 'app-programacion-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './programacion-list.component.html'})
export class ProgramacionListComponent implements OnInit, OnDestroy {

  programacionService = inject(ProgramacionService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  programacions?: ProgramacionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@programacion.delete.success:Programacion was removed successfully.`,
      'programacion.calificacion.programacion.referenced': $localize`:@@programacion.calificacion.programacion.referenced:This entity is still referenced by Calificacion ${details?.id} via field Programacion.`
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
    this.programacionService.getAllProgramacions()
        .subscribe({
          next: (data) => this.programacions = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idProgramacion: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.programacionService.deleteProgramacion(idProgramacion)
        .subscribe({
          next: () => this.router.navigate(['/programacions'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/programacions'], {
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
