import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { TareaService } from 'app/tarea/tarea.service';
import { TareaDTO } from 'app/tarea/tarea.model';


@Component({
  selector: 'app-tarea-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './tarea-list.component.html'})
export class TareaListComponent implements OnInit, OnDestroy {

  tareaService = inject(TareaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  tareas?: TareaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@tarea.delete.success:Tarea was removed successfully.`    };
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
    this.tareaService.getAllTareas()
        .subscribe({
          next: (data) => this.tareas = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idTarea: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.tareaService.deleteTarea(idTarea)
        .subscribe({
          next: () => this.router.navigate(['/tareas'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
