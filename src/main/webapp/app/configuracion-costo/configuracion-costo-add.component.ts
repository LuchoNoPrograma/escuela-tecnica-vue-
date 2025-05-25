import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ConfiguracionCostoService } from 'app/configuracion-costo/configuracion-costo.service';
import { ConfiguracionCostoDTO } from 'app/configuracion-costo/configuracion-costo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-configuracion-costo-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './configuracion-costo-add.component.html'
})
export class ConfiguracionCostoAddComponent implements OnInit {

  configuracionCostoService = inject(ConfiguracionCostoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  programaValues?: Map<number,string>;

  addForm = new FormGroup({
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
      created: $localize`:@@configuracionCosto.create.success:Configuracion Costo was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.configuracionCostoService.getProgramaValues()
        .subscribe({
          next: (data) => this.programaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ConfiguracionCostoDTO(this.addForm.value);
    this.configuracionCostoService.createConfiguracionCosto(data)
        .subscribe({
          next: () => this.router.navigate(['/configuracionCostos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
