import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { AdministrativoService } from 'app/administrativo/administrativo.service';
import { AdministrativoDTO } from 'app/administrativo/administrativo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-administrativo-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './administrativo-edit.component.html'
})
export class AdministrativoEditComponent implements OnInit {

  administrativoService = inject(AdministrativoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  personaValues?: Map<number,string>;
  currentIdAdministrativo?: number;

  editForm = new FormGroup({
    idAdministrativo: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecIngreso: new FormControl(null),
    fecSalida: new FormControl(null),
    habil: new FormControl(false),
    persona: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@administrativo.update.success:Administrativo was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdAdministrativo = +this.route.snapshot.params['idAdministrativo'];
    this.administrativoService.getPersonaValues()
        .subscribe({
          next: (data) => this.personaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.administrativoService.getAdministrativo(this.currentIdAdministrativo!)
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
    const data = new AdministrativoDTO(this.editForm.value);
    this.administrativoService.updateAdministrativo(this.currentIdAdministrativo!, data)
        .subscribe({
          next: () => this.router.navigate(['/administrativos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
