import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CronogramaModuloService } from 'app/cronograma-modulo/cronograma-modulo.service';
import { CronogramaModuloDTO } from 'app/cronograma-modulo/cronograma-modulo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-cronograma-modulo-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './cronograma-modulo-add.component.html'
})
export class CronogramaModuloAddComponent implements OnInit {

  cronogramaModuloService = inject(CronogramaModuloService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  planEstudioDetalleValues?: Map<number,string>;
  grupoValues?: Map<number,string>;
  docenteValues?: Map<number,string>;

  addForm = new FormGroup({
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
      created: $localize`:@@cronogramaModulo.create.success:Cronograma Modulo was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new CronogramaModuloDTO(this.addForm.value);
    this.cronogramaModuloService.createCronogramaModulo(data)
        .subscribe({
          next: () => this.router.navigate(['/cronogramaModulos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
