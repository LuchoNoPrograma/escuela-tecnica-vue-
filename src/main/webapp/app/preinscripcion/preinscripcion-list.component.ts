import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { PreinscripcionService } from 'app/preinscripcion/preinscripcion.service';
import { PreinscripcionDTO } from 'app/preinscripcion/preinscripcion.model';
import { SearchFilterComponent } from 'app/common/list-helper/search-filter.component';
import { SortingComponent } from 'app/common/list-helper/sorting.component';
import { getListParams } from 'app/common/utils';
import { PagedModel, PaginationComponent } from 'app/common/list-helper/pagination.component';


@Component({
  selector: 'app-preinscripcion-list',
  imports: [CommonModule, SearchFilterComponent ,SortingComponent, PaginationComponent, RouterLink],
  templateUrl: './preinscripcion-list.component.html'})
export class PreinscripcionListComponent implements OnInit, OnDestroy {

  preinscripcionService = inject(PreinscripcionService);
  errorHandler = inject(ErrorHandler);
  route = inject(ActivatedRoute);
  router = inject(Router);
  preinscripcions?: PagedModel<PreinscripcionDTO>;
  navigationSubscription?: Subscription;

  sortOptions = {
    'idUsuReg,ASC': $localize`:@@preinscripcion.list.sort.idUsuReg,ASC:Sort by Id Usu Reg (Ascending)`, 
    'idUsuMod,ASC': $localize`:@@preinscripcion.list.sort.idUsuMod,ASC:Sort by Id Usu Mod (Ascending)`, 
    'idPreinscripcion,ASC': $localize`:@@preinscripcion.list.sort.idPreinscripcion,ASC:Sort by Id Preinscripcion (Ascending)`
  }

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@preinscripcion.delete.success:Preinscripcion was removed successfully.`    };
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
    this.preinscripcionService.getAllPreinscripcions(getListParams(this.route))
        .subscribe({
          next: (data) => this.preinscripcions = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPreinscripcion: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.preinscripcionService.deletePreinscripcion(idPreinscripcion)
        .subscribe({
          next: () => this.router.navigate(['/preinscripcions'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
