import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ObservacionMonografiaService } from 'app/observacion-monografia/observacion-monografia.service';
import { ObservacionMonografiaDTO } from 'app/observacion-monografia/observacion-monografia.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-observacion-monografia-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './observacion-monografia-add.component.html'
})
export class ObservacionMonografiaAddComponent implements OnInit {

  observacionMonografiaService = inject(ObservacionMonografiaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  revisionMonografiaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    descripcion: new FormControl(null, [Validators.required]),
    revisionMonografia: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@observacionMonografia.create.success:Observacion Monografia was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.observacionMonografiaService.getRevisionMonografiaValues()
        .subscribe({
          next: (data) => this.revisionMonografiaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ObservacionMonografiaDTO(this.addForm.value);
    this.observacionMonografiaService.createObservacionMonografia(data)
        .subscribe({
          next: () => this.router.navigate(['/observacionMonografias'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
