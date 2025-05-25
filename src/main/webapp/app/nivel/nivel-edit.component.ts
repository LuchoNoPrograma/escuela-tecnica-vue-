import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { NivelService } from 'app/nivel/nivel.service';
import { NivelDTO } from 'app/nivel/nivel.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-nivel-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './nivel-edit.component.html'
})
export class NivelEditComponent implements OnInit {

  nivelService = inject(NivelService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentIdNivel?: number;

  editForm = new FormGroup({
    idNivel: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreNivel: new FormControl(null, [Validators.required, Validators.maxLength(100)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@nivel.update.success:Nivel was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdNivel = +this.route.snapshot.params['idNivel'];
    this.nivelService.getNivel(this.currentIdNivel!)
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
    const data = new NivelDTO(this.editForm.value);
    this.nivelService.updateNivel(this.currentIdNivel!, data)
        .subscribe({
          next: () => this.router.navigate(['/nivels'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
