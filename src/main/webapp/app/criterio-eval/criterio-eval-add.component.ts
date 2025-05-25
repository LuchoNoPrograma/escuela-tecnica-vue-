import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CriterioEvalService } from 'app/criterio-eval/criterio-eval.service';
import { CriterioEvalDTO } from 'app/criterio-eval/criterio-eval.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-criterio-eval-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './criterio-eval-add.component.html'
})
export class CriterioEvalAddComponent implements OnInit {

  criterioEvalService = inject(CriterioEvalService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  cronogramaModValues?: Map<number,string>;

  addForm = new FormGroup({
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
      created: $localize`:@@criterioEval.create.success:Criterio Eval was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.criterioEvalService.getCronogramaModValues()
        .subscribe({
          next: (data) => this.cronogramaModValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new CriterioEvalDTO(this.addForm.value);
    this.criterioEvalService.createCriterioEval(data)
        .subscribe({
          next: () => this.router.navigate(['/criterioEvals'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
