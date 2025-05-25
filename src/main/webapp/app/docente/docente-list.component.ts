import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { DocenteService } from 'app/docente/docente.service';
import { DocenteDTO } from 'app/docente/docente.model';


@Component({
  selector: 'app-docente-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './docente-list.component.html'})
export class DocenteListComponent implements OnInit, OnDestroy {

  docenteService = inject(DocenteService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  docentes?: DocenteDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@docente.delete.success:Docente was removed successfully.`,
      'docente.cronogramaModulo.docente.referenced': $localize`:@@docente.cronogramaModulo.docente.referenced:This entity is still referenced by Cronograma Modulo ${details?.id} via field Docente.`,
      'docente.revisionMonografia.docente.referenced': $localize`:@@docente.revisionMonografia.docente.referenced:This entity is still referenced by Revision Monografia ${details?.id} via field Docente.`
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
    this.docenteService.getAllDocentes()
        .subscribe({
          next: (data) => this.docentes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idDocente: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.docenteService.deleteDocente(idDocente)
        .subscribe({
          next: () => this.router.navigate(['/docentes'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/docentes'], {
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
