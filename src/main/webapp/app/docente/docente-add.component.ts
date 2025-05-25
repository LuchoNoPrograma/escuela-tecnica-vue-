import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { DocenteService } from 'app/docente/docente.service';
import { DocenteDTO } from 'app/docente/docente.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-docente-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './docente-add.component.html'
})
export class DocenteAddComponent implements OnInit {

  docenteService = inject(DocenteService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  personaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nroResolucion: new FormControl(null),
    fecInicioContrato: new FormControl(null, [Validators.required]),
    fecFinContrato: new FormControl(null),
    persona: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@docente.create.success:Docente was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.docenteService.getPersonaValues()
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
    const data = new DocenteDTO(this.addForm.value);
    this.docenteService.createDocente(data)
        .subscribe({
          next: () => this.router.navigate(['/docentes'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
