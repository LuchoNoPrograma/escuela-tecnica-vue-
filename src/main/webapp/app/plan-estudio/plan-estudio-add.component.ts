import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PlanEstudioService } from 'app/plan-estudio/plan-estudio.service';
import { PlanEstudioDTO } from 'app/plan-estudio/plan-estudio.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-plan-estudio-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './plan-estudio-add.component.html'
})
export class PlanEstudioAddComponent implements OnInit {

  planEstudioService = inject(PlanEstudioService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  programaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    anho: new FormControl(null, [Validators.required]),
    vigente: new FormControl(false),
    programa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@planEstudio.create.success:Plan Estudio was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.planEstudioService.getProgramaValues()
        .subscribe({
          next: (data) => this.programaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new PlanEstudioDTO(this.addForm.value);
    this.planEstudioService.createPlanEstudio(data)
        .subscribe({
          next: () => this.router.navigate(['/planEstudios'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
