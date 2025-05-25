import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { MatriculaService } from 'app/matricula/matricula.service';
import { MatriculaDTO } from 'app/matricula/matricula.model';


@Component({
  selector: 'app-matricula-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './matricula-list.component.html'})
export class MatriculaListComponent implements OnInit, OnDestroy {

  matriculaService = inject(MatriculaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  matriculas?: MatriculaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@matricula.delete.success:Matricula was removed successfully.`,
      'matricula.certificado.matricula.referenced': $localize`:@@matricula.certificado.matricula.referenced:This entity is still referenced by Certificado ${details?.id} via field Matricula.`,
      'matricula.monografia.matricula.referenced': $localize`:@@matricula.monografia.matricula.referenced:This entity is still referenced by Monografia ${details?.id} via field Matricula.`,
      'matricula.planPago.matricula.referenced': $localize`:@@matricula.planPago.matricula.referenced:This entity is still referenced by Plan Pago ${details?.id} via field Matricula.`,
      'matricula.programacion.matricula.referenced': $localize`:@@matricula.programacion.matricula.referenced:This entity is still referenced by Programacion ${details?.id} via field Matricula.`,
      'matricula.titulacion.matricula.referenced': $localize`:@@matricula.titulacion.matricula.referenced:This entity is still referenced by Titulacion ${details?.id} via field Matricula.`
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
    this.matriculaService.getAllMatriculas()
        .subscribe({
          next: (data) => this.matriculas = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(codMatricula: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.matriculaService.deleteMatricula(codMatricula)
        .subscribe({
          next: () => this.router.navigate(['/matriculas'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/matriculas'], {
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
