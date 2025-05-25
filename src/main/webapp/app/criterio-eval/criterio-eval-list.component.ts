import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CriterioEvalService } from 'app/criterio-eval/criterio-eval.service';
import { CriterioEvalDTO } from 'app/criterio-eval/criterio-eval.model';


@Component({
  selector: 'app-criterio-eval-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './criterio-eval-list.component.html'})
export class CriterioEvalListComponent implements OnInit, OnDestroy {

  criterioEvalService = inject(CriterioEvalService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  criterioEvals?: CriterioEvalDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@criterioEval.delete.success:Criterio Eval was removed successfully.`,
      'criterioEval.calificacion.criterioEval.referenced': $localize`:@@criterioEval.calificacion.criterioEval.referenced:This entity is still referenced by Calificacion ${details?.id} via field Criterio Eval.`
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
    this.criterioEvalService.getAllCriterioEvals()
        .subscribe({
          next: (data) => this.criterioEvals = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idCriterioEval: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.criterioEvalService.deleteCriterioEval(idCriterioEval)
        .subscribe({
          next: () => this.router.navigate(['/criterioEvals'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/criterioEvals'], {
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
