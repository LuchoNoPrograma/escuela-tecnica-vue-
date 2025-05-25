import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CategoriaService } from 'app/categoria/categoria.service';
import { CategoriaDTO } from 'app/categoria/categoria.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-categoria-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './categoria-edit.component.html'
})
export class CategoriaEditComponent implements OnInit {

  categoriaService = inject(CategoriaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentIdCategoria?: number;

  editForm = new FormGroup({
    idCategoria: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreCat: new FormControl(null, [Validators.maxLength(100)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@categoria.update.success:Categoria was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdCategoria = +this.route.snapshot.params['idCategoria'];
    this.categoriaService.getCategoria(this.currentIdCategoria!)
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
    const data = new CategoriaDTO(this.editForm.value);
    this.categoriaService.updateCategoria(this.currentIdCategoria!, data)
        .subscribe({
          next: () => this.router.navigate(['/categorias'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
