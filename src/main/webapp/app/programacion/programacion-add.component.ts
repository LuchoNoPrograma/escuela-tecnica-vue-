import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ProgramacionService } from 'app/programacion/programacion.service';
import { ProgramacionDTO } from 'app/programacion/programacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-programacion-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './programacion-add.component.html'
})
export class ProgramacionAddComponent implements OnInit {

  programacionService = inject(ProgramacionService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  cronogramaModuloValues?: Map<number,string>;
  matriculaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    observacion: new FormControl(null, [Validators.maxLength(255)]),
    fecProgramacion: new FormControl(null, [Validators.required]),
    estCalificacion: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    tipoProgramacion: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    calificacionFinal: new FormControl(null),
    cronogramaModulo: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@programacion.create.success:Programacion was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.programacionService.getCronogramaModuloValues()
        .subscribe({
          next: (data) => this.cronogramaModuloValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.programacionService.getMatriculaValues()
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
    const data = new ProgramacionDTO(this.addForm.value);
    this.programacionService.createProgramacion(data)
        .subscribe({
          next: () => this.router.navigate(['/programacions'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
