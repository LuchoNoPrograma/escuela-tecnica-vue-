import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ComprobantePagoService } from 'app/comprobante-pago/comprobante-pago.service';
import { ComprobantePagoDTO } from 'app/comprobante-pago/comprobante-pago.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-comprobante-pago-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './comprobante-pago-add.component.html'
})
export class ComprobantePagoAddComponent implements OnInit {

  comprobantePagoService = inject(ComprobantePagoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  listaDetallesPagosValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    codComprobante: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    monto: new FormControl(null, [Validators.required, validNumeric(12, 2)]),
    fecComprobante: new FormControl(null, [Validators.required]),
    tipoDeposito: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    listaDetallesPagos: new FormControl([])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@comprobantePago.create.success:Comprobante Pago was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.comprobantePagoService.getListaDetallesPagosValues()
        .subscribe({
          next: (data) => this.listaDetallesPagosValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ComprobantePagoDTO(this.addForm.value);
    this.comprobantePagoService.createComprobantePago(data)
        .subscribe({
          next: () => this.router.navigate(['/comprobantePagos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
