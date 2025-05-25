import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TareaService } from 'app/tarea/tarea.service';
import { TareaDTO } from 'app/tarea/tarea.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-tarea-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './tarea-add.component.html'
})
export class TareaAddComponent implements OnInit {

  tareaService = inject(TareaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  agrupaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreTarea: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    descripTarea: new FormControl(null),
    estTarea: new FormControl(false),
    agrupa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@tarea.create.success:Tarea was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.tareaService.getAgrupaValues()
        .subscribe({
          next: (data) => this.agrupaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new TareaDTO(this.addForm.value);
    this.tareaService.createTarea(data)
        .subscribe({
          next: () => this.router.navigate(['/tareas'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
