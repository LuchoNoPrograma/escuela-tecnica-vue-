import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PlanPagoService } from 'app/plan-pago/plan-pago.service';
import { PlanPagoDTO } from 'app/plan-pago/plan-pago.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-plan-pago-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './plan-pago-add.component.html'
})
export class PlanPagoAddComponent implements OnInit {

  planPagoService = inject(PlanPagoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  matriculaValues?: Map<number,string>;

  addForm = new FormGroup({
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
      created: $localize`:@@planPago.create.success:Plan Pago was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.planPagoService.getMatriculaValues()
        .subscribe({
          next: (data) => this.matriculaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new PlanPagoDTO(this.addForm.value);
    this.planPagoService.createPlanPago(data)
        .subscribe({
          next: () => this.router.navigate(['/planPagos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
