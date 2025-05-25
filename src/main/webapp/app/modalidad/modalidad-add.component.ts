import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ModalidadService } from 'app/modalidad/modalidad.service';
import { ModalidadDTO } from 'app/modalidad/modalidad.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-modalidad-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './modalidad-add.component.html'
})
export class ModalidadAddComponent {

  modalidadService = inject(ModalidadService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombre: new FormControl(null, [Validators.maxLength(50)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@modalidad.create.success:Modalidad was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ModalidadDTO(this.addForm.value);
    this.modalidadService.createModalidad(data)
        .subscribe({
          next: () => this.router.navigate(['/modalidads'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
