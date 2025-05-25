import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { DetallePagoService } from 'app/detalle-pago/detalle-pago.service';
import { DetallePagoDTO } from 'app/detalle-pago/detalle-pago.model';


@Component({
  selector: 'app-detalle-pago-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './detalle-pago-list.component.html'})
export class DetallePagoListComponent implements OnInit, OnDestroy {

  detallePagoService = inject(DetallePagoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  detallePagoes?: DetallePagoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@detallePago.delete.success:Detalle Pago was removed successfully.`    };
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
    this.detallePagoService.getAllDetallePagoes()
        .subscribe({
          next: (data) => this.detallePagoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idDetallePago: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.detallePagoService.deleteDetallePago(idDetallePago)
        .subscribe({
          next: () => this.router.navigate(['/detallePagos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
