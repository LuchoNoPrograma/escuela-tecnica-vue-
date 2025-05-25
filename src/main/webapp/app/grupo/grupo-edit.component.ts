import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { GrupoService } from 'app/grupo/grupo.service';
import { GrupoDTO } from 'app/grupo/grupo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-grupo-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './grupo-edit.component.html'
})
export class GrupoEditComponent implements OnInit {

  grupoService = inject(GrupoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  versionValues?: Map<number,string>;
  programaValues?: Map<number,string>;
  currentIdGrupo?: number;

  editForm = new FormGroup({
    idGrupo: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreGrupo: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    estGrupo: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    version: new FormControl(null),
    programa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@grupo.update.success:Grupo was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdGrupo = +this.route.snapshot.params['idGrupo'];
    this.grupoService.getVersionValues()
        .subscribe({
          next: (data) => this.versionValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.grupoService.getProgramaValues()
        .subscribe({
          next: (data) => this.programaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.grupoService.getGrupo(this.currentIdGrupo!)
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
    const data = new GrupoDTO(this.editForm.value);
    this.grupoService.updateGrupo(this.currentIdGrupo!, data)
        .subscribe({
          next: () => this.router.navigate(['/grupos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
