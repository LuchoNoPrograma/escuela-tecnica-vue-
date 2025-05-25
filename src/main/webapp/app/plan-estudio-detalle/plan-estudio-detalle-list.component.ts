import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { PlanEstudioDetalleService } from 'app/plan-estudio-detalle/plan-estudio-detalle.service';
import { PlanEstudioDetalleDTO } from 'app/plan-estudio-detalle/plan-estudio-detalle.model';


@Component({
  selector: 'app-plan-estudio-detalle-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './plan-estudio-detalle-list.component.html'})
export class PlanEstudioDetalleListComponent implements OnInit, OnDestroy {

  planEstudioDetalleService = inject(PlanEstudioDetalleService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  planEstudioDetalles?: PlanEstudioDetalleDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@planEstudioDetalle.delete.success:Plan Estudio Detalle was removed successfully.`,
      'planEstudioDetalle.cronogramaModulo.planEstudioDetalle.referenced': $localize`:@@planEstudioDetalle.cronogramaModulo.planEstudioDetalle.referenced:This entity is still referenced by Cronograma Modulo ${details?.id} via field Plan Estudio Detalle.`
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
    this.planEstudioDetalleService.getAllPlanEstudioDetalles()
        .subscribe({
          next: (data) => this.planEstudioDetalles = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPlanEstudioDetalle: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.planEstudioDetalleService.deletePlanEstudioDetalle(idPlanEstudioDetalle)
        .subscribe({
          next: () => this.router.navigate(['/planEstudioDetalles'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/planEstudioDetalles'], {
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
