import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { MonografiaService } from 'app/monografia/monografia.service';
import { MonografiaDTO } from 'app/monografia/monografia.model';


@Component({
  selector: 'app-monografia-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './monografia-list.component.html'})
export class MonografiaListComponent implements OnInit, OnDestroy {

  monografiaService = inject(MonografiaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  monografias?: MonografiaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@monografia.delete.success:Monografia was removed successfully.`,
      'monografia.revisionMonografia.monografia.referenced': $localize`:@@monografia.revisionMonografia.monografia.referenced:This entity is still referenced by Revision Monografia ${details?.id} via field Monografia.`
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
    this.monografiaService.getAllMonografias()
        .subscribe({
          next: (data) => this.monografias = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idMonografia: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.monografiaService.deleteMonografia(idMonografia)
        .subscribe({
          next: () => this.router.navigate(['/monografias'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/monografias'], {
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
