import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { RolService } from 'app/rol/rol.service';
import { RolDTO } from 'app/rol/rol.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-rol-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './rol-edit.component.html'
})
export class RolEditComponent implements OnInit {

  rolService = inject(RolService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentIdPermiso?: number;

  editForm = new FormGroup({
    idPermiso: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    estCargo: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    descripcionCargo: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@rol.update.success:Rol was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPermiso = +this.route.snapshot.params['idPermiso'];
    this.rolService.getRol(this.currentIdPermiso!)
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
    const data = new RolDTO(this.editForm.value);
    this.rolService.updateRol(this.currentIdPermiso!, data)
        .subscribe({
          next: () => this.router.navigate(['/rols'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
