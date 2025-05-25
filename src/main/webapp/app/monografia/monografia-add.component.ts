import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { MonografiaService } from 'app/monografia/monografia.service';
import { MonografiaDTO } from 'app/monografia/monografia.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-monografia-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './monografia-add.component.html'
})
export class MonografiaAddComponent implements OnInit {

  monografiaService = inject(MonografiaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  matriculaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    titulo: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    estMonografia: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    notaFinal: new FormControl(null, [Validators.required]),
    matricula: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@monografia.create.success:Monografia was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.monografiaService.getMatriculaValues()
        .subscribe({
          next: (data) => this.matriculaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new MonografiaDTO(this.addForm.value);
    this.monografiaService.createMonografia(data)
        .subscribe({
          next: () => this.router.navigate(['/monografias'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
