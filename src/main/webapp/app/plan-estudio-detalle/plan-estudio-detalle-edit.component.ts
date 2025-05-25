import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PlanEstudioDetalleService } from 'app/plan-estudio-detalle/plan-estudio-detalle.service';
import { PlanEstudioDetalleDTO } from 'app/plan-estudio-detalle/plan-estudio-detalle.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-plan-estudio-detalle-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './plan-estudio-detalle-edit.component.html'
})
export class PlanEstudioDetalleEditComponent implements OnInit {

  planEstudioDetalleService = inject(PlanEstudioDetalleService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  nivelValues?: Map<number,string>;
  moduloValues?: Map<number,string>;
  planEstudioValues?: Map<number,string>;
  currentIdPlanEstudioDetalle?: number;

  editForm = new FormGroup({
    idPlanEstudioDetalle: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    cargaHoraria: new FormControl(null, [Validators.required]),
    creditos: new FormControl(null, [Validators.required, validNumeric(8, 2)]),
    orden: new FormControl(null, [Validators.required]),
    sigla: new FormControl(null, [Validators.required, Validators.maxLength(10)]),
    nivel: new FormControl(null, [Validators.required]),
    modulo: new FormControl(null, [Validators.required]),
    planEstudio: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@planEstudioDetalle.update.success:Plan Estudio Detalle was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPlanEstudioDetalle = +this.route.snapshot.params['idPlanEstudioDetalle'];
    this.planEstudioDetalleService.getNivelValues()
        .subscribe({
          next: (data) => this.nivelValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.planEstudioDetalleService.getModuloValues()
        .subscribe({
          next: (data) => this.moduloValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.planEstudioDetalleService.getPlanEstudioValues()
        .subscribe({
          next: (data) => this.planEstudioValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.planEstudioDetalleService.getPlanEstudioDetalle(this.currentIdPlanEstudioDetalle!)
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
    const data = new PlanEstudioDetalleDTO(this.editForm.value);
    this.planEstudioDetalleService.updatePlanEstudioDetalle(this.currentIdPlanEstudioDetalle!, data)
        .subscribe({
          next: () => this.router.navigate(['/planEstudioDetalles'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
