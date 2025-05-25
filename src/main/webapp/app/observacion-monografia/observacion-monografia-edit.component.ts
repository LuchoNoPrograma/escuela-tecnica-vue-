import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ObservacionMonografiaService } from 'app/observacion-monografia/observacion-monografia.service';
import { ObservacionMonografiaDTO } from 'app/observacion-monografia/observacion-monografia.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-observacion-monografia-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './observacion-monografia-edit.component.html'
})
export class ObservacionMonografiaEditComponent implements OnInit {

  observacionMonografiaService = inject(ObservacionMonografiaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  revisionMonografiaValues?: Map<number,string>;
  currentIdObservacionMonografia?: number;

  editForm = new FormGroup({
    idObservacionMonografia: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    descripcion: new FormControl(null, [Validators.required]),
    revisionMonografia: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@observacionMonografia.update.success:Observacion Monografia was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdObservacionMonografia = +this.route.snapshot.params['idObservacionMonografia'];
    this.observacionMonografiaService.getRevisionMonografiaValues()
        .subscribe({
          next: (data) => this.revisionMonografiaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.observacionMonografiaService.getObservacionMonografia(this.currentIdObservacionMonografia!)
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
    const data = new ObservacionMonografiaDTO(this.editForm.value);
    this.observacionMonografiaService.updateObservacionMonografia(this.currentIdObservacionMonografia!, data)
        .subscribe({
          next: () => this.router.navigate(['/observacionMonografias'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
