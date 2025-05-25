import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CertificadoService } from 'app/certificado/certificado.service';
import { CertificadoDTO } from 'app/certificado/certificado.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-certificado-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './certificado-edit.component.html'
})
export class CertificadoEditComponent implements OnInit {

  certificadoService = inject(CertificadoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  administrativoValues?: Map<number,string>;
  matriculaValues?: Map<number,string>;
  currentIdCertificado?: number;

  editForm = new FormGroup({
    idCertificado: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fechaEmision: new FormControl(null),
    estCertificado: new FormControl(null, [Validators.maxLength(55)]),
    administrativo: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@certificado.update.success:Certificado was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdCertificado = +this.route.snapshot.params['idCertificado'];
    this.certificadoService.getAdministrativoValues()
        .subscribe({
          next: (data) => this.administrativoValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.certificadoService.getMatriculaValues()
        .subscribe({
          next: (data) => this.matriculaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.certificadoService.getCertificado(this.currentIdCertificado!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new CertificadoDTO(this.editForm.value);
    this.certificadoService.updateCertificado(this.currentIdCertificado!, data)
        .subscribe({
          next: () => this.router.navigate(['/certificados'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
