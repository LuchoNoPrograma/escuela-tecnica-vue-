import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CalificacionService } from 'app/calificacion/calificacion.service';
import { CalificacionDTO } from 'app/calificacion/calificacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-calificacion-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './calificacion-add.component.html'
})
export class CalificacionAddComponent implements OnInit {

  calificacionService = inject(CalificacionService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  criterioEvalValues?: Map<number,string>;
  programacionValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nota: new FormControl(null, [validNumeric(7, 2)]),
    criterioEval: new FormControl(null, [Validators.required]),
    programacion: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@calificacion.create.success:Calificacion was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new CalificacionDTO(this.addForm.value);
    this.calificacionService.createCalificacion(data)
        .subscribe({
          next: () => this.router.navigate(['/calificacions'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
