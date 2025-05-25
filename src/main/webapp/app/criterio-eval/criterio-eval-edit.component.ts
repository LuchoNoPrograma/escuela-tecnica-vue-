import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CriterioEvalService } from 'app/criterio-eval/criterio-eval.service';
import { CriterioEvalDTO } from 'app/criterio-eval/criterio-eval.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-criterio-eval-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './criterio-eval-edit.component.html'
})
export class CriterioEvalEditComponent implements OnInit {

  criterioEvalService = inject(CriterioEvalService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  cronogramaModValues?: Map<number,string>;
  currentIdCriterioEval?: number;

  editForm = new FormGroup({
    idCriterioEval: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreCrit: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    descripcion: new FormControl(null, [Validators.maxLength(155)]),
    ponderacion: new FormControl(null, [Validators.required]),
    orden: new FormControl(null, [Validators.required]),
    cronogramaMod: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@criterioEval.update.success:Criterio Eval was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdCriterioEval = +this.route.snapshot.params['idCriterioEval'];
    this.criterioEvalService.getCronogramaModValues()
        .subscribe({
          next: (data) => this.cronogramaModValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.criterioEvalService.getCriterioEval(this.currentIdCriterioEval!)
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
    const data = new CriterioEvalDTO(this.editForm.value);
    this.criterioEvalService.updateCriterioEval(this.currentIdCriterioEval!, data)
        .subscribe({
          next: () => this.router.navigate(['/criterioEvals'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
