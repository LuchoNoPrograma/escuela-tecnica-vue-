import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ModuloService } from 'app/modulo/modulo.service';
import { ModuloDTO } from 'app/modulo/modulo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-modulo-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './modulo-edit.component.html'
})
export class ModuloEditComponent implements OnInit {

  moduloService = inject(ModuloService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentIdModulo?: number;

  editForm = new FormGroup({
    idModulo: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreMod: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    competencia: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@modulo.update.success:Modulo was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdModulo = +this.route.snapshot.params['idModulo'];
    this.moduloService.getModulo(this.currentIdModulo!)
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
    const data = new ModuloDTO(this.editForm.value);
    this.moduloService.updateModulo(this.currentIdModulo!, data)
        .subscribe({
          next: () => this.router.navigate(['/modulos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
