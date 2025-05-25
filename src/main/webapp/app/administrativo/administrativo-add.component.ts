import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { AdministrativoService } from 'app/administrativo/administrativo.service';
import { AdministrativoDTO } from 'app/administrativo/administrativo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-administrativo-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './administrativo-add.component.html'
})
export class AdministrativoAddComponent implements OnInit {

  administrativoService = inject(AdministrativoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  personaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecIngreso: new FormControl(null),
    fecSalida: new FormControl(null),
    habil: new FormControl(false),
    persona: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@administrativo.create.success:Administrativo was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.administrativoService.getPersonaValues()
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
    const data = new AdministrativoDTO(this.addForm.value);
    this.administrativoService.createAdministrativo(data)
        .subscribe({
          next: () => this.router.navigate(['/administrativos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
