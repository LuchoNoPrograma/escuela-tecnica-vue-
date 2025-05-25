import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { RolService } from 'app/rol/rol.service';
import { RolDTO } from 'app/rol/rol.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-rol-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './rol-add.component.html'
})
export class RolAddComponent {

  rolService = inject(RolService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    estCargo: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    descripcionCargo: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@rol.create.success:Rol was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new RolDTO(this.addForm.value);
    this.rolService.createRol(data)
        .subscribe({
          next: () => this.router.navigate(['/rols'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
