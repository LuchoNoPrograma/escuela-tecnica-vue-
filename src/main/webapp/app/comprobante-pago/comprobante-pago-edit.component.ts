import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ComprobantePagoService } from 'app/comprobante-pago/comprobante-pago.service';
import { ComprobantePagoDTO } from 'app/comprobante-pago/comprobante-pago.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-comprobante-pago-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './comprobante-pago-edit.component.html'
})
export class ComprobantePagoEditComponent implements OnInit {

  comprobantePagoService = inject(ComprobantePagoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  listaDetallesPagosValues?: Map<number,string>;
  currentIdComprobante?: number;

  editForm = new FormGroup({
    idComprobante: new FormControl({ value: null, disabled: true }),
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
      updated: $localize`:@@comprobantePago.update.success:Comprobante Pago was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdComprobante = +this.route.snapshot.params['idComprobante'];
    this.comprobantePagoService.getListaDetallesPagosValues()
        .subscribe({
          next: (data) => this.listaDetallesPagosValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.comprobantePagoService.getComprobantePago(this.currentIdComprobante!)
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
    const data = new ComprobantePagoDTO(this.editForm.value);
    this.comprobantePagoService.updateComprobantePago(this.currentIdComprobante!, data)
        .subscribe({
          next: () => this.router.navigate(['/comprobantePagos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
