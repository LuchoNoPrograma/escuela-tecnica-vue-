import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CertificadoService } from 'app/certificado/certificado.service';
import { CertificadoDTO } from 'app/certificado/certificado.model';


@Component({
  selector: 'app-certificado-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './certificado-list.component.html'})
export class CertificadoListComponent implements OnInit, OnDestroy {

  certificadoService = inject(CertificadoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  certificadoes?: CertificadoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@certificado.delete.success:Certificado was removed successfully.`    };
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
    this.certificadoService.getAllCertificadoes()
        .subscribe({
          next: (data) => this.certificadoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idCertificado: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.certificadoService.deleteCertificado(idCertificado)
        .subscribe({
          next: () => this.router.navigate(['/certificados'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

}
