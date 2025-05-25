import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ProgramaService } from 'app/programa/programa.service';
import { ProgramaDTO } from 'app/programa/programa.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-programa-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './programa-add.component.html'
})
export class ProgramaAddComponent implements OnInit {

  programaService = inject(ProgramaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  modalidadValues?: Map<number,string>;
  categoriaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombrePrograma: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    estPrograma: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    modalidad: new FormControl(null, [Validators.required]),
    categoria: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@programa.create.success:Programa was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.programaService.getModalidadValues()
        .subscribe({
          next: (data) => this.modalidadValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.programaService.getCategoriaValues()
        .subscribe({
          next: (data) => this.categoriaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ProgramaDTO(this.addForm.value);
    this.programaService.createPrograma(data)
        .subscribe({
          next: () => this.router.navigate(['/programas'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
