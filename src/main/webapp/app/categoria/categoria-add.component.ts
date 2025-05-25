import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CategoriaService } from 'app/categoria/categoria.service';
import { CategoriaDTO } from 'app/categoria/categoria.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-categoria-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './categoria-add.component.html'
})
export class CategoriaAddComponent {

  categoriaService = inject(CategoriaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreCat: new FormControl(null, [Validators.maxLength(100)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@categoria.create.success:Categoria was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new CategoriaDTO(this.addForm.value);
    this.categoriaService.createCategoria(data)
        .subscribe({
          next: () => this.router.navigate(['/categorias'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
