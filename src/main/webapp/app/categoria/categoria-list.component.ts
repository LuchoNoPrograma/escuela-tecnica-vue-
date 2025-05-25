import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CategoriaService } from 'app/categoria/categoria.service';
import { CategoriaDTO } from 'app/categoria/categoria.model';


@Component({
  selector: 'app-categoria-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './categoria-list.component.html'})
export class CategoriaListComponent implements OnInit, OnDestroy {

  categoriaService = inject(CategoriaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  categorias?: CategoriaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@categoria.delete.success:Categoria was removed successfully.`,
      'categoria.programa.categoria.referenced': $localize`:@@categoria.programa.categoria.referenced:This entity is still referenced by Programa ${details?.id} via field Categoria.`
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
    this.categoriaService.getAllCategorias()
        .subscribe({
          next: (data) => this.categorias = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idCategoria: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.categoriaService.deleteCategoria(idCategoria)
        .subscribe({
          next: () => this.router.navigate(['/categorias'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/categorias'], {
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
