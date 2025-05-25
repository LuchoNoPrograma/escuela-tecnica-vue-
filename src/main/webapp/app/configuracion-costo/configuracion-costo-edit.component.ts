import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ConfiguracionCostoService } from 'app/configuracion-costo/configuracion-costo.service';
import { ConfiguracionCostoDTO } from 'app/configuracion-costo/configuracion-costo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-configuracion-costo-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './configuracion-costo-edit.component.html'
})
export class ConfiguracionCostoEditComponent implements OnInit {

  configuracionCostoService = inject(ConfiguracionCostoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  programaValues?: Map<number,string>;
  currentIdConfiguracionCosto?: number;

  editForm = new FormGroup({
    idConfiguracionCosto: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    montoConfig: new FormControl(null, [Validators.required, validNumeric(12, 2)]),
    fecInicioVig: new FormControl(null, [Validators.required]),
    fecFinVig: new FormControl(null),
    estConfig: new FormControl(false),
    concepto: new FormControl(null, [Validators.required, Validators.maxLength(55)]),
    programa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@configuracionCosto.update.success:Configuracion Costo was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdConfiguracionCosto = +this.route.snapshot.params['idConfiguracionCosto'];
    this.configuracionCostoService.getProgramaValues()
        .subscribe({
          next: (data) => this.programaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.configuracionCostoService.getConfiguracionCosto(this.currentIdConfiguracionCosto!)
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
    const data = new ConfiguracionCostoDTO(this.editForm.value);
    this.configuracionCostoService.updateConfiguracionCosto(this.currentIdConfiguracionCosto!, data)
        .subscribe({
          next: () => this.router.navigate(['/configuracionCostos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
