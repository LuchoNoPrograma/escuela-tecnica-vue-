import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TitulacionService } from 'app/titulacion/titulacion.service';
import { TitulacionDTO } from 'app/titulacion/titulacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-titulacion-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './titulacion-edit.component.html'
})
export class TitulacionEditComponent implements OnInit {

  titulacionService = inject(TitulacionService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  matriculaValues?: Map<number,string>;
  currentIdTitulacion?: number;

  editForm = new FormGroup({
    idTitulacion: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecEmision: new FormControl(null, [Validators.required]),
    codTitulo: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    urlDoc: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@titulacion.update.success:Titulacion was updated successfully.`,
      TITULACION_MATRICULA_UNIQUE: $localize`:@@Exists.titulacion.matricula:This Matricula is already referenced by another Titulacion.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdTitulacion = +this.route.snapshot.params['idTitulacion'];
    this.titulacionService.getMatriculaValues()
        .subscribe({
          next: (data) => this.matriculaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.titulacionService.getTitulacion(this.currentIdTitulacion!)
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
    const data = new TitulacionDTO(this.editForm.value);
    this.titulacionService.updateTitulacion(this.currentIdTitulacion!, data)
        .subscribe({
          next: () => this.router.navigate(['/titulacions'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
