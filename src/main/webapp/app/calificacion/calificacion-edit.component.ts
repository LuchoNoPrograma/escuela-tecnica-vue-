import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CalificacionService } from 'app/calificacion/calificacion.service';
import { CalificacionDTO } from 'app/calificacion/calificacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-calificacion-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './calificacion-edit.component.html'
})
export class CalificacionEditComponent implements OnInit {

  calificacionService = inject(CalificacionService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  criterioEvalValues?: Map<number,string>;
  programacionValues?: Map<number,string>;
  currentIdCalificacion?: number;

  editForm = new FormGroup({
    idCalificacion: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nota: new FormControl(null, [validNumeric(7, 2)]),
    criterioEval: new FormControl(null, [Validators.required]),
    programacion: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@calificacion.update.success:Calificacion was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdCalificacion = +this.route.snapshot.params['idCalificacion'];
    this.calificacionService.getCriterioEvalValues()
        .subscribe({
          next: (data) => this.criterioEvalValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.calificacionService.getProgramacionValues()
        .subscribe({
          next: (data) => this.programacionValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.calificacionService.getCalificacion(this.currentIdCalificacion!)
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
    const data = new CalificacionDTO(this.editForm.value);
    this.calificacionService.updateCalificacion(this.currentIdCalificacion!, data)
        .subscribe({
          next: () => this.router.navigate(['/calificacions'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
