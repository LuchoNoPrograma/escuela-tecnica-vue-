import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { TitulacionService } from 'app/titulacion/titulacion.service';
import { TitulacionDTO } from 'app/titulacion/titulacion.model';


@Component({
  selector: 'app-titulacion-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './titulacion-list.component.html'})
export class TitulacionListComponent implements OnInit, OnDestroy {

  titulacionService = inject(TitulacionService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  titulacions?: TitulacionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@titulacion.delete.success:Titulacion was removed successfully.`    };
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
    this.titulacionService.getAllTitulacions()
        .subscribe({
          next: (data) => this.titulacions = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idTitulacion: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.titulacionService.deleteTitulacion(idTitulacion)
        .subscribe({
          next: () => this.router.navigate(['/titulacions'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
