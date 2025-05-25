import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PlanEstudioDetalleService } from 'app/plan-estudio-detalle/plan-estudio-detalle.service';
import { PlanEstudioDetalleDTO } from 'app/plan-estudio-detalle/plan-estudio-detalle.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validNumeric } from 'app/common/utils';


@Component({
  selector: 'app-plan-estudio-detalle-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './plan-estudio-detalle-add.component.html'
})
export class PlanEstudioDetalleAddComponent implements OnInit {

  planEstudioDetalleService = inject(PlanEstudioDetalleService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  nivelValues?: Map<number,string>;
  moduloValues?: Map<number,string>;
  planEstudioValues?: Map<number,string>;

  addForm = new FormGroup({
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
      created: $localize`:@@planEstudioDetalle.create.success:Plan Estudio Detalle was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new PlanEstudioDetalleDTO(this.addForm.value);
    this.planEstudioDetalleService.createPlanEstudioDetalle(data)
        .subscribe({
          next: () => this.router.navigate(['/planEstudioDetalles'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
