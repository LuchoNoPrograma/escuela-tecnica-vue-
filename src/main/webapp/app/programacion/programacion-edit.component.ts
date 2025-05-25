import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ProgramacionService } from 'app/programacion/programacion.service';
import { ProgramacionDTO } from 'app/programacion/programacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-programacion-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './programacion-edit.component.html'
})
export class ProgramacionEditComponent implements OnInit {

  programacionService = inject(ProgramacionService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  cronogramaModuloValues?: Map<number,string>;
  matriculaValues?: Map<number,string>;
  currentIdProgramacion?: number;

  editForm = new FormGroup({
    idProgramacion: new FormControl({ value: null, disabled: true }),
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
      updated: $localize`:@@programacion.update.success:Programacion was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdProgramacion = +this.route.snapshot.params['idProgramacion'];
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
    this.programacionService.getProgramacion(this.currentIdProgramacion!)
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
    const data = new ProgramacionDTO(this.editForm.value);
    this.programacionService.updateProgramacion(this.currentIdProgramacion!, data)
        .subscribe({
          next: () => this.router.navigate(['/programacions'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
