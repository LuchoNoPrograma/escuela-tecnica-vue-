import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { OcupaService } from 'app/ocupa/ocupa.service';
import { OcupaDTO } from 'app/ocupa/ocupa.model';


@Component({
  selector: 'app-ocupa-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './ocupa-list.component.html'})
export class OcupaListComponent implements OnInit, OnDestroy {

  ocupaService = inject(OcupaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  ocupas?: OcupaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@ocupa.delete.success:Ocupa was removed successfully.`    };
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
    this.ocupaService.getAllOcupas()
        .subscribe({
          next: (data) => this.ocupas = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(estOcupa: string) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.ocupaService.deleteOcupa(estOcupa)
        .subscribe({
          next: () => this.router.navigate(['/ocupas'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
