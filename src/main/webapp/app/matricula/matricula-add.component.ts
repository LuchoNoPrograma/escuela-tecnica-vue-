import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { MatriculaService } from 'app/matricula/matricula.service';
import { MatriculaDTO } from 'app/matricula/matricula.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-matricula-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './matricula-add.component.html'
})
export class MatriculaAddComponent implements OnInit {

  matriculaService = inject(MatriculaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  grupoValues?: Map<number,string>;
  personaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecMatriculacion: new FormControl(null),
    estMatricula: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    grupo: new FormControl(null, [Validators.required]),
    persona: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@matricula.create.success:Matricula was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new MatriculaDTO(this.addForm.value);
    this.matriculaService.createMatricula(data)
        .subscribe({
          next: () => this.router.navigate(['/matriculas'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
