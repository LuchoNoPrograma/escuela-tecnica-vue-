import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { RevisionMonografiaService } from 'app/revision-monografia/revision-monografia.service';
import { RevisionMonografiaDTO } from 'app/revision-monografia/revision-monografia.model';


@Component({
  selector: 'app-revision-monografia-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './revision-monografia-list.component.html'})
export class RevisionMonografiaListComponent implements OnInit, OnDestroy {

  revisionMonografiaService = inject(RevisionMonografiaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  revisionMonografias?: RevisionMonografiaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@revisionMonografia.delete.success:Revision Monografia was removed successfully.`,
      'revisionMonografia.observacionMonografia.revisionMonografia.referenced': $localize`:@@revisionMonografia.observacionMonografia.revisionMonografia.referenced:This entity is still referenced by Observacion Monografia ${details?.id} via field Revision Monografia.`
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
    this.revisionMonografiaService.getAllRevisionMonografias()
        .subscribe({
          next: (data) => this.revisionMonografias = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idRevisionMonografia: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.revisionMonografiaService.deleteRevisionMonografia(idRevisionMonografia)
        .subscribe({
          next: () => this.router.navigate(['/revisionMonografias'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/revisionMonografias'], {
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
