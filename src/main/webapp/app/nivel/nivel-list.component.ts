import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { NivelService } from 'app/nivel/nivel.service';
import { NivelDTO } from 'app/nivel/nivel.model';


@Component({
  selector: 'app-nivel-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './nivel-list.component.html'})
export class NivelListComponent implements OnInit, OnDestroy {

  nivelService = inject(NivelService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  nivels?: NivelDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@nivel.delete.success:Nivel was removed successfully.`,
      'nivel.planEstudioDetalle.nivel.referenced': $localize`:@@nivel.planEstudioDetalle.nivel.referenced:This entity is still referenced by Plan Estudio Detalle ${details?.id} via field Nivel.`
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
    this.nivelService.getAllNivels()
        .subscribe({
          next: (data) => this.nivels = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idNivel: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.nivelService.deleteNivel(idNivel)
        .subscribe({
          next: () => this.router.navigate(['/nivels'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/nivels'], {
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
