import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ProgramaService } from 'app/programa/programa.service';
import { ProgramaDTO } from 'app/programa/programa.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-programa-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './programa-edit.component.html'
})
export class ProgramaEditComponent implements OnInit {

  programaService = inject(ProgramaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  modalidadValues?: Map<number,string>;
  categoriaValues?: Map<number,string>;
  currentIdPrograma?: number;

  editForm = new FormGroup({
    idPrograma: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombrePrograma: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    estPrograma: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    modalidad: new FormControl(null, [Validators.required]),
    categoria: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@programa.update.success:Programa was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdPrograma = +this.route.snapshot.params['idPrograma'];
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
    this.programaService.getPrograma(this.currentIdPrograma!)
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
    const data = new ProgramaDTO(this.editForm.value);
    this.programaService.updatePrograma(this.currentIdPrograma!, data)
        .subscribe({
          next: () => this.router.navigate(['/programas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
