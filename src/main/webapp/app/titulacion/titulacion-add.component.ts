import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TitulacionService } from 'app/titulacion/titulacion.service';
import { TitulacionDTO } from 'app/titulacion/titulacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-titulacion-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './titulacion-add.component.html'
})
export class TitulacionAddComponent implements OnInit {

  titulacionService = inject(TitulacionService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  matriculaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecEmision: new FormControl(null, [Validators.required]),
    codTitulo: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    urlDoc: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@titulacion.create.success:Titulacion was created successfully.`,
      TITULACION_MATRICULA_UNIQUE: $localize`:@@Exists.titulacion.matricula:This Matricula is already referenced by another Titulacion.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.titulacionService.getMatriculaValues()
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
    const data = new TitulacionDTO(this.addForm.value);
    this.titulacionService.createTitulacion(data)
        .subscribe({
          next: () => this.router.navigate(['/titulacions'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
