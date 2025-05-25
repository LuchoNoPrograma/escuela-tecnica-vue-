import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { PlanPagoService } from 'app/plan-pago/plan-pago.service';
import { PlanPagoDTO } from 'app/plan-pago/plan-pago.model';


@Component({
  selector: 'app-plan-pago-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './plan-pago-list.component.html'})
export class PlanPagoListComponent implements OnInit, OnDestroy {

  planPagoService = inject(PlanPagoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  planPagoes?: PlanPagoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@planPago.delete.success:Plan Pago was removed successfully.`,
      'planPago.detallePago.planPago.referenced': $localize`:@@planPago.detallePago.planPago.referenced:This entity is still referenced by Detalle Pago ${details?.id} via field Plan Pago.`
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
    this.planPagoService.getAllPlanPagoes()
        .subscribe({
          next: (data) => this.planPagoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPlanPago: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.planPagoService.deletePlanPago(idPlanPago)
        .subscribe({
          next: () => this.router.navigate(['/planPagos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/planPagos'], {
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
