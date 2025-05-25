import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ModalidadService } from 'app/modalidad/modalidad.service';
import { ModalidadDTO } from 'app/modalidad/modalidad.model';


@Component({
  selector: 'app-modalidad-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './modalidad-list.component.html'})
export class ModalidadListComponent implements OnInit, OnDestroy {

  modalidadService = inject(ModalidadService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  modalidads?: ModalidadDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@modalidad.delete.success:Modalidad was removed successfully.`,
      'modalidad.programa.modalidad.referenced': $localize`:@@modalidad.programa.modalidad.referenced:This entity is still referenced by Programa ${details?.id} via field Modalidad.`
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
    this.modalidadService.getAllModalidads()
        .subscribe({
          next: (data) => this.modalidads = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idModalidad: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.modalidadService.deleteModalidad(idModalidad)
        .subscribe({
          next: () => this.router.navigate(['/modalidads'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/modalidads'], {
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
