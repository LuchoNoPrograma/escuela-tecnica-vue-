import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { DocenteService } from 'app/docente/docente.service';
import { DocenteDTO } from 'app/docente/docente.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-docente-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './docente-edit.component.html'
})
export class DocenteEditComponent implements OnInit {

  docenteService = inject(DocenteService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  personaValues?: Map<number,string>;
  currentIdDocente?: number;

  editForm = new FormGroup({
    idDocente: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nroResolucion: new FormControl(null),
    fecInicioContrato: new FormControl(null, [Validators.required]),
    fecFinContrato: new FormControl(null),
    persona: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@docente.update.success:Docente was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdDocente = +this.route.snapshot.params['idDocente'];
    this.docenteService.getPersonaValues()
        .subscribe({
          next: (data) => this.personaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.docenteService.getDocente(this.currentIdDocente!)
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
    const data = new DocenteDTO(this.editForm.value);
    this.docenteService.updateDocente(this.currentIdDocente!, data)
        .subscribe({
          next: () => this.router.navigate(['/docentes'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
