import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { RolService } from 'app/rol/rol.service';
import { RolDTO } from 'app/rol/rol.model';


@Component({
  selector: 'app-rol-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './rol-list.component.html'})
export class RolListComponent implements OnInit, OnDestroy {

  rolService = inject(RolService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  rols?: RolDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@rol.delete.success:Rol was removed successfully.`,
      'rol.ocupa.rol.referenced': $localize`:@@rol.ocupa.rol.referenced:This entity is still referenced by Ocupa ${details?.id} via field Rol.`,
      'rol.tarea.agrupa.referenced': $localize`:@@rol.tarea.agrupa.referenced:This entity is still referenced by Tarea ${details?.id} via field Agrupa.`
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
    this.rolService.getAllRols()
        .subscribe({
          next: (data) => this.rols = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPermiso: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.rolService.deleteRol(idPermiso)
        .subscribe({
          next: () => this.router.navigate(['/rols'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/rols'], {
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
