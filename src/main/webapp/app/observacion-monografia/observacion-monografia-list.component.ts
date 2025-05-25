import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ObservacionMonografiaService } from 'app/observacion-monografia/observacion-monografia.service';
import { ObservacionMonografiaDTO } from 'app/observacion-monografia/observacion-monografia.model';


@Component({
  selector: 'app-observacion-monografia-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './observacion-monografia-list.component.html'})
export class ObservacionMonografiaListComponent implements OnInit, OnDestroy {

  observacionMonografiaService = inject(ObservacionMonografiaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  observacionMonografias?: ObservacionMonografiaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@observacionMonografia.delete.success:Observacion Monografia was removed successfully.`    };
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
    this.observacionMonografiaService.getAllObservacionMonografias()
        .subscribe({
          next: (data) => this.observacionMonografias = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idObservacionMonografia: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.observacionMonografiaService.deleteObservacionMonografia(idObservacionMonografia)
        .subscribe({
          next: () => this.router.navigate(['/observacionMonografias'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
