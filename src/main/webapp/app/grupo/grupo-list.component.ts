import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { GrupoService } from 'app/grupo/grupo.service';
import { GrupoDTO } from 'app/grupo/grupo.model';


@Component({
  selector: 'app-grupo-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './grupo-list.component.html'})
export class GrupoListComponent implements OnInit, OnDestroy {

  grupoService = inject(GrupoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  grupoes?: GrupoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@grupo.delete.success:Grupo was removed successfully.`,
      'grupo.cronogramaModulo.grupo.referenced': $localize`:@@grupo.cronogramaModulo.grupo.referenced:This entity is still referenced by Cronograma Modulo ${details?.id} via field Grupo.`,
      'grupo.matricula.grupo.referenced': $localize`:@@grupo.matricula.grupo.referenced:This entity is still referenced by Matricula ${details?.id} via field Grupo.`
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
    this.grupoService.getAllGrupoes()
        .subscribe({
          next: (data) => this.grupoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idGrupo: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.grupoService.deleteGrupo(idGrupo)
        .subscribe({
          next: () => this.router.navigate(['/grupos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/grupos'], {
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
