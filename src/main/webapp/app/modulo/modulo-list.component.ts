import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ModuloService } from 'app/modulo/modulo.service';
import { ModuloDTO } from 'app/modulo/modulo.model';


@Component({
  selector: 'app-modulo-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './modulo-list.component.html'})
export class ModuloListComponent implements OnInit, OnDestroy {

  moduloService = inject(ModuloService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  moduloes?: ModuloDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@modulo.delete.success:Modulo was removed successfully.`,
      'modulo.planEstudioDetalle.modulo.referenced': $localize`:@@modulo.planEstudioDetalle.modulo.referenced:This entity is still referenced by Plan Estudio Detalle ${details?.id} via field Modulo.`
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
    this.moduloService.getAllModuloes()
        .subscribe({
          next: (data) => this.moduloes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idModulo: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.moduloService.deleteModulo(idModulo)
        .subscribe({
          next: () => this.router.navigate(['/modulos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/modulos'], {
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
