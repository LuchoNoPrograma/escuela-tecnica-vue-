import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ConfiguracionCostoService } from 'app/configuracion-costo/configuracion-costo.service';
import { ConfiguracionCostoDTO } from 'app/configuracion-costo/configuracion-costo.model';


@Component({
  selector: 'app-configuracion-costo-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './configuracion-costo-list.component.html'})
export class ConfiguracionCostoListComponent implements OnInit, OnDestroy {

  configuracionCostoService = inject(ConfiguracionCostoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  configuracionCostoes?: ConfiguracionCostoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@configuracionCosto.delete.success:Configuracion Costo was removed successfully.`    };
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
    this.configuracionCostoService.getAllConfiguracionCostoes()
        .subscribe({
          next: (data) => this.configuracionCostoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idConfiguracionCosto: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.configuracionCostoService.deleteConfiguracionCosto(idConfiguracionCosto)
        .subscribe({
          next: () => this.router.navigate(['/configuracionCostos'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
