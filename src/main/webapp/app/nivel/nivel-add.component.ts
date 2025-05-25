import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { NivelService } from 'app/nivel/nivel.service';
import { NivelDTO } from 'app/nivel/nivel.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-nivel-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './nivel-add.component.html'
})
export class NivelAddComponent {

  nivelService = inject(NivelService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreNivel: new FormControl(null, [Validators.required, Validators.maxLength(100)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@nivel.create.success:Nivel was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new NivelDTO(this.addForm.value);
    this.nivelService.createNivel(data)
        .subscribe({
          next: () => this.router.navigate(['/nivels'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
