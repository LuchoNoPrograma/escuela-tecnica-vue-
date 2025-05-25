import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { PlanEstudioService } from 'app/plan-estudio/plan-estudio.service';
import { PlanEstudioDTO } from 'app/plan-estudio/plan-estudio.model';


@Component({
  selector: 'app-plan-estudio-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './plan-estudio-list.component.html'})
export class PlanEstudioListComponent implements OnInit, OnDestroy {

  planEstudioService = inject(PlanEstudioService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  planEstudios?: PlanEstudioDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@planEstudio.delete.success:Plan Estudio was removed successfully.`,
      'planEstudio.planEstudioDetalle.planEstudio.referenced': $localize`:@@planEstudio.planEstudioDetalle.planEstudio.referenced:This entity is still referenced by Plan Estudio Detalle ${details?.id} via field Plan Estudio.`
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
    this.planEstudioService.getAllPlanEstudios()
        .subscribe({
          next: (data) => this.planEstudios = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPlanEstudio: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.planEstudioService.deletePlanEstudio(idPlanEstudio)
        .subscribe({
          next: () => this.router.navigate(['/planEstudios'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/planEstudios'], {
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
