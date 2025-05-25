import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { DetallePagoService } from 'app/detalle-pago/detalle-pago.service';
import { DetallePagoDTO } from 'app/detalle-pago/detalle-pago.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-detalle-pago-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './detalle-pago-add.component.html'
})
export class DetallePagoAddComponent implements OnInit {

  detallePagoService = inject(DetallePagoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  planPagoValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    montoPagadoDetalle: new FormControl(null, [validNumeric(12, 2)]),
    planPago: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@detallePago.create.success:Detalle Pago was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.detallePagoService.getPlanPagoValues()
        .subscribe({
          next: (data) => this.planPagoValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new DetallePagoDTO(this.addForm.value);
    this.detallePagoService.createDetallePago(data)
        .subscribe({
          next: () => this.router.navigate(['/detallePagos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
