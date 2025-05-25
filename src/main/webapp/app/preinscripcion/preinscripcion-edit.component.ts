import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PreinscripcionService } from 'app/preinscripcion/preinscripcion.service';
import { PreinscripcionDTO } from 'app/preinscripcion/preinscripcion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-preinscripcion-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './preinscripcion-edit.component.html'
})
export class PreinscripcionEditComponent implements OnInit {

  preinscripcionService = inject(PreinscripcionService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentIdPreinscripcion?: number;

  editForm = new FormGroup({
    idPreinscripcion: new FormControl({ value: null, disabled: true }),
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
      updated: $localize`:@@preinscripcion.update.success:Preinscripcion was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPreinscripcion = +this.route.snapshot.params['idPreinscripcion'];
    this.preinscripcionService.getPreinscripcion(this.currentIdPreinscripcion!)
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
    const data = new PreinscripcionDTO(this.editForm.value);
    this.preinscripcionService.updatePreinscripcion(this.currentIdPreinscripcion!, data)
        .subscribe({
          next: () => this.router.navigate(['/preinscripcions'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
