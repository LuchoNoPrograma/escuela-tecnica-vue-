import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { MatriculaService } from 'app/matricula/matricula.service';
import { MatriculaDTO } from 'app/matricula/matricula.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-matricula-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './matricula-edit.component.html'
})
export class MatriculaEditComponent implements OnInit {

  matriculaService = inject(MatriculaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  grupoValues?: Map<number,string>;
  personaValues?: Map<number,string>;
  currentCodMatricula?: number;

  editForm = new FormGroup({
    codMatricula: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecMatriculacion: new FormControl(null),
    estMatricula: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    grupo: new FormControl(null, [Validators.required]),
    persona: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@matricula.update.success:Matricula was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentCodMatricula = +this.route.snapshot.params['codMatricula'];
    this.matriculaService.getGrupoValues()
        .subscribe({
          next: (data) => this.grupoValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.matriculaService.getPersonaValues()
        .subscribe({
          next: (data) => this.personaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.matriculaService.getMatricula(this.currentCodMatricula!)
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
    const data = new MatriculaDTO(this.editForm.value);
    this.matriculaService.updateMatricula(this.currentCodMatricula!, data)
        .subscribe({
          next: () => this.router.navigate(['/matriculas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
