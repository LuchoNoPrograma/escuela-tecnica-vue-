import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ComprobantePagoService } from 'app/comprobante-pago/comprobante-pago.service';
import { ComprobantePagoDTO } from 'app/comprobante-pago/comprobante-pago.model';


@Component({
  selector: 'app-comprobante-pago-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './comprobante-pago-list.component.html'})
export class ComprobantePagoListComponent implements OnInit, OnDestroy {

  comprobantePagoService = inject(ComprobantePagoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  comprobantePagoes?: ComprobantePagoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@comprobantePago.delete.success:Comprobante Pago was removed successfully.`    };
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
    this.comprobantePagoService.getAllComprobantePagoes()
        .subscribe({
          next: (data) => this.comprobantePagoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idComprobante: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.comprobantePagoService.deleteComprobantePago(idComprobante)
        .subscribe({
          next: () => this.router.navigate(['/comprobantePagos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
