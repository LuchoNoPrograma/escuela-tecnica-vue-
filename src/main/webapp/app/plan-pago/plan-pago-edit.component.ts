import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PlanPagoService } from 'app/plan-pago/plan-pago.service';
import { PlanPagoDTO } from 'app/plan-pago/plan-pago.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-plan-pago-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './plan-pago-edit.component.html'
})
export class PlanPagoEditComponent implements OnInit {

  planPagoService = inject(PlanPagoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  matriculaValues?: Map<number,string>;
  currentIdPlanPago?: number;

  editForm = new FormGroup({
    idPlanPago: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    deudaTotal: new FormControl(null, [Validators.required, validNumeric(12, 2)]),
    pagadoTotal: new FormControl(null, [validNumeric(12, 2)]),
    estPlanPago: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    concepto: new FormControl(null, [Validators.required, Validators.maxLength(155)]),
    matricula: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@planPago.update.success:Plan Pago was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPlanPago = +this.route.snapshot.params['idPlanPago'];
    this.planPagoService.getMatriculaValues()
        .subscribe({
          next: (data) => this.matriculaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.planPagoService.getPlanPago(this.currentIdPlanPago!)
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
    const data = new PlanPagoDTO(this.editForm.value);
    this.planPagoService.updatePlanPago(this.currentIdPlanPago!, data)
        .subscribe({
          next: () => this.router.navigate(['/planPagos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
