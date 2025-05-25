import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CalificacionService } from 'app/calificacion/calificacion.service';
import { CalificacionDTO } from 'app/calificacion/calificacion.model';


@Component({
  selector: 'app-calificacion-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './calificacion-list.component.html'})
export class CalificacionListComponent implements OnInit, OnDestroy {

  calificacionService = inject(CalificacionService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  calificacions?: CalificacionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@calificacion.delete.success:Calificacion was removed successfully.`    };
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
    this.calificacionService.getAllCalificacions()
        .subscribe({
          next: (data) => this.calificacions = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idCalificacion: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.calificacionService.deleteCalificacion(idCalificacion)
        .subscribe({
          next: () => this.router.navigate(['/calificacions'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
