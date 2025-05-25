import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CertificadoService } from 'app/certificado/certificado.service';
import { CertificadoDTO } from 'app/certificado/certificado.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-certificado-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './certificado-add.component.html'
})
export class CertificadoAddComponent implements OnInit {

  certificadoService = inject(CertificadoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  administrativoValues?: Map<number,string>;
  matriculaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fechaEmision: new FormControl(null),
    estCertificado: new FormControl(null, [Validators.maxLength(55)]),
    administrativo: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@certificado.create.success:Certificado was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new CertificadoDTO(this.addForm.value);
    this.certificadoService.createCertificado(data)
        .subscribe({
          next: () => this.router.navigate(['/certificados'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
