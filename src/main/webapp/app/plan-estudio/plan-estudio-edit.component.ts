import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PlanEstudioService } from 'app/plan-estudio/plan-estudio.service';
import { PlanEstudioDTO } from 'app/plan-estudio/plan-estudio.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-plan-estudio-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './plan-estudio-edit.component.html'
})
export class PlanEstudioEditComponent implements OnInit {

  planEstudioService = inject(PlanEstudioService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  programaValues?: Map<number,string>;
  currentIdPlanEstudio?: number;

  editForm = new FormGroup({
    idPlanEstudio: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    anho: new FormControl(null, [Validators.required]),
    vigente: new FormControl(false),
    programa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@planEstudio.update.success:Plan Estudio was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPlanEstudio = +this.route.snapshot.params['idPlanEstudio'];
    this.planEstudioService.getProgramaValues()
        .subscribe({
          next: (data) => this.programaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.planEstudioService.getPlanEstudio(this.currentIdPlanEstudio!)
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
    const data = new PlanEstudioDTO(this.editForm.value);
    this.planEstudioService.updatePlanEstudio(this.currentIdPlanEstudio!, data)
        .subscribe({
          next: () => this.router.navigate(['/planEstudios'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
