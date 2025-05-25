import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TareaService } from 'app/tarea/tarea.service';
import { TareaDTO } from 'app/tarea/tarea.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-tarea-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './tarea-edit.component.html'
})
export class TareaEditComponent implements OnInit {

  tareaService = inject(TareaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  agrupaValues?: Map<number,string>;
  currentIdTarea?: number;

  editForm = new FormGroup({
    idTarea: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreTarea: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    descripTarea: new FormControl(null),
    estTarea: new FormControl(false),
    agrupa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@tarea.update.success:Tarea was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdTarea = +this.route.snapshot.params['idTarea'];
    this.tareaService.getAgrupaValues()
        .subscribe({
          next: (data) => this.agrupaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.tareaService.getTarea(this.currentIdTarea!)
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
    const data = new TareaDTO(this.editForm.value);
    this.tareaService.updateTarea(this.currentIdTarea!, data)
        .subscribe({
          next: () => this.router.navigate(['/tareas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
