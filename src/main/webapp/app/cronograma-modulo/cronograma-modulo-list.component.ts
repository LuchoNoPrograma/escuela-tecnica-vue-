import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CronogramaModuloService } from 'app/cronograma-modulo/cronograma-modulo.service';
import { CronogramaModuloDTO } from 'app/cronograma-modulo/cronograma-modulo.model';


@Component({
  selector: 'app-cronograma-modulo-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './cronograma-modulo-list.component.html'})
export class CronogramaModuloListComponent implements OnInit, OnDestroy {

  cronogramaModuloService = inject(CronogramaModuloService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  cronogramaModuloes?: CronogramaModuloDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@cronogramaModulo.delete.success:Cronograma Modulo was removed successfully.`,
      'cronogramaModulo.criterioEval.cronogramaMod.referenced': $localize`:@@cronogramaModulo.criterioEval.cronogramaMod.referenced:This entity is still referenced by Criterio Eval ${details?.id} via field Cronograma Mod.`,
      'cronogramaModulo.programacion.cronogramaModulo.referenced': $localize`:@@cronogramaModulo.programacion.cronogramaModulo.referenced:This entity is still referenced by Programacion ${details?.id} via field Cronograma Modulo.`
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
    this.cronogramaModuloService.getAllCronogramaModuloes()
        .subscribe({
          next: (data) => this.cronogramaModuloes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idCronogramaMod: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.cronogramaModuloService.deleteCronogramaModulo(idCronogramaMod)
        .subscribe({
          next: () => this.router.navigate(['/cronogramaModulos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/cronogramaModulos'], {
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
