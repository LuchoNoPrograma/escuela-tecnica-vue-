import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PersonaService } from 'app/persona/persona.service';
import { PersonaDTO } from 'app/persona/persona.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-persona-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './persona-edit.component.html'
})
export class PersonaEditComponent implements OnInit {

  personaService = inject(PersonaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentIdPersona?: number;

  editForm = new FormGroup({
    idPersona: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    apPaterno: new FormControl(null, [Validators.required, Validators.maxLength(55)]),
    apMaterno: new FormControl(null, [Validators.maxLength(55)]),
    ci: new FormControl(null, [Validators.required, Validators.maxLength(20)]),
    nroCelular: new FormControl(null, [Validators.required, Validators.maxLength(20)]),
    correo: new FormControl(null, [Validators.maxLength(55)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@persona.update.success:Persona was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPersona = +this.route.snapshot.params['idPersona'];
    this.personaService.getPersona(this.currentIdPersona!)
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
    const data = new PersonaDTO(this.editForm.value);
    this.personaService.updatePersona(this.currentIdPersona!, data)
        .subscribe({
          next: () => this.router.navigate(['/personas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
