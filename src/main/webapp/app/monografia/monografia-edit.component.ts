import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { MonografiaService } from 'app/monografia/monografia.service';
import { MonografiaDTO } from 'app/monografia/monografia.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-monografia-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './monografia-edit.component.html'
})
export class MonografiaEditComponent implements OnInit {

  monografiaService = inject(MonografiaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  matriculaValues?: Map<number,string>;
  currentIdMonografia?: number;

  editForm = new FormGroup({
    idMonografia: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    titulo: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    estMonografia: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    notaFinal: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@monografia.update.success:Monografia was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdMonografia = +this.route.snapshot.params['idMonografia'];
    this.monografiaService.getMatriculaValues()
        .subscribe({
          next: (data) => this.matriculaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.monografiaService.getMonografia(this.currentIdMonografia!)
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
    const data = new MonografiaDTO(this.editForm.value);
    this.monografiaService.updateMonografia(this.currentIdMonografia!, data)
        .subscribe({
          next: () => this.router.navigate(['/monografias'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
