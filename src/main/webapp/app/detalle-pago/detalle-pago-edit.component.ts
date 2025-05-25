import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { DetallePagoService } from 'app/detalle-pago/detalle-pago.service';
import { DetallePagoDTO } from 'app/detalle-pago/detalle-pago.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-detalle-pago-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './detalle-pago-edit.component.html'
})
export class DetallePagoEditComponent implements OnInit {

  detallePagoService = inject(DetallePagoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  planPagoValues?: Map<number,string>;
  currentIdDetallePago?: number;

  editForm = new FormGroup({
    idDetallePago: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    montoPagadoDetalle: new FormControl(null, [validNumeric(12, 2)]),
    planPago: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@detallePago.update.success:Detalle Pago was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdDetallePago = +this.route.snapshot.params['idDetallePago'];
    this.detallePagoService.getPlanPagoValues()
        .subscribe({
          next: (data) => this.planPagoValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.detallePagoService.getDetallePago(this.currentIdDetallePago!)
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
    const data = new DetallePagoDTO(this.editForm.value);
    this.detallePagoService.updateDetallePago(this.currentIdDetallePago!, data)
        .subscribe({
          next: () => this.router.navigate(['/detallePagos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
