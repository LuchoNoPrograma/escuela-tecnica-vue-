import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PreinscripcionService } from 'app/preinscripcion/preinscripcion.service';
import { PreinscripcionDTO } from 'app/preinscripcion/preinscripcion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-preinscripcion-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './preinscripcion-add.component.html'
})
export class PreinscripcionAddComponent {

  preinscripcionService = inject(PreinscripcionService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombres: new FormControl(null, [Validators.required, Validators.maxLength(55)]),
    paterno: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    materno: new FormControl(null, [Validators.maxLength(35)]),
    ci: new FormControl(null, [Validators.maxLength(255)]),
    celular: new FormControl(null, [Validators.maxLength(15)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@preinscripcion.create.success:Preinscripcion was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new PreinscripcionDTO(this.addForm.value);
    this.preinscripcionService.createPreinscripcion(data)
        .subscribe({
          next: () => this.router.navigate(['/preinscripcions'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
