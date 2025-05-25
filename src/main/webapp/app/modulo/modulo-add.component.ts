import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ModuloService } from 'app/modulo/modulo.service';
import { ModuloDTO } from 'app/modulo/modulo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-modulo-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './modulo-add.component.html'
})
export class ModuloAddComponent {

  moduloService = inject(ModuloService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreMod: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    competencia: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@modulo.create.success:Modulo was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ModuloDTO(this.addForm.value);
    this.moduloService.createModulo(data)
        .subscribe({
          next: () => this.router.navigate(['/modulos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
