import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { OcupaService } from 'app/ocupa/ocupa.service';
import { OcupaDTO } from 'app/ocupa/ocupa.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-ocupa-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './ocupa-edit.component.html'
})
export class OcupaEditComponent implements OnInit {

  ocupaService = inject(OcupaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  rolValues?: Map<number,string>;
  usuarioValues?: Map<number,string>;
  currentEstOcupa?: string;

  editForm = new FormGroup({
    estOcupa: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    rol: new FormControl(null, [Validators.required]),
    usuario: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@ocupa.update.success:Ocupa was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentEstOcupa = this.route.snapshot.params['estOcupa'];
    this.ocupaService.getRolValues()
        .subscribe({
          next: (data) => this.rolValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.ocupaService.getUsuarioValues()
        .subscribe({
          next: (data) => this.usuarioValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.ocupaService.getOcupa(this.currentEstOcupa!)
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
    const data = new OcupaDTO(this.editForm.value);
    this.ocupaService.updateOcupa(this.currentEstOcupa!, data)
        .subscribe({
          next: () => this.router.navigate(['/ocupas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
