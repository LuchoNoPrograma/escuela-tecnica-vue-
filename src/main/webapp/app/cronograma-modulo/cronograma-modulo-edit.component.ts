import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CronogramaModuloService } from 'app/cronograma-modulo/cronograma-modulo.service';
import { CronogramaModuloDTO } from 'app/cronograma-modulo/cronograma-modulo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-cronograma-modulo-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './cronograma-modulo-edit.component.html'
})
export class CronogramaModuloEditComponent implements OnInit {

  cronogramaModuloService = inject(CronogramaModuloService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  planEstudioDetalleValues?: Map<number,string>;
  grupoValues?: Map<number,string>;
  docenteValues?: Map<number,string>;
  currentIdCronogramaMod?: number;

  editForm = new FormGroup({
    idCronogramaMod: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecInicio: new FormControl(null),
    fecFin: new FormControl(null),
    estado: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    planEstudioDetalle: new FormControl(null, [Validators.required]),
    grupo: new FormControl(null, [Validators.required]),
    docente: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@cronogramaModulo.update.success:Cronograma Modulo was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdCronogramaMod = +this.route.snapshot.params['idCronogramaMod'];
    this.cronogramaModuloService.getPlanEstudioDetalleValues()
        .subscribe({
          next: (data) => this.planEstudioDetalleValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.cronogramaModuloService.getGrupoValues()
        .subscribe({
          next: (data) => this.grupoValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.cronogramaModuloService.getDocenteValues()
        .subscribe({
          next: (data) => this.docenteValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.cronogramaModuloService.getCronogramaModulo(this.currentIdCronogramaMod!)
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
    const data = new CronogramaModuloDTO(this.editForm.value);
    this.cronogramaModuloService.updateCronogramaModulo(this.currentIdCronogramaMod!, data)
        .subscribe({
          next: () => this.router.navigate(['/cronogramaModulos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
