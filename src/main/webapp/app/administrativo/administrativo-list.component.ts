import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { AdministrativoService } from 'app/administrativo/administrativo.service';
import { AdministrativoDTO } from 'app/administrativo/administrativo.model';


@Component({
  selector: 'app-administrativo-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './administrativo-list.component.html'})
export class AdministrativoListComponent implements OnInit, OnDestroy {

  administrativoService = inject(AdministrativoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  administrativoes?: AdministrativoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@administrativo.delete.success:Administrativo was removed successfully.`,
      'administrativo.certificado.administrativo.referenced': $localize`:@@administrativo.certificado.administrativo.referenced:This entity is still referenced by Certificado ${details?.id} via field Administrativo.`,
      'administrativo.revisionMonografia.administrativo.referenced': $localize`:@@administrativo.revisionMonografia.administrativo.referenced:This entity is still referenced by Revision Monografia ${details?.id} via field Administrativo.`
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
    this.administrativoService.getAllAdministrativoes()
        .subscribe({
          next: (data) => this.administrativoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idAdministrativo: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.administrativoService.deleteAdministrativo(idAdministrativo)
        .subscribe({
          next: () => this.router.navigate(['/administrativos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/administrativos'], {
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
