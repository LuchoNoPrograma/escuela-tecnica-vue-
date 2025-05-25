import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { RevisionMonografiaService } from 'app/revision-monografia/revision-monografia.service';
import { RevisionMonografiaDTO } from 'app/revision-monografia/revision-monografia.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-revision-monografia-edit',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './revision-monografia-edit.component.html'
})
export class RevisionMonografiaEditComponent implements OnInit {

  revisionMonografiaService = inject(RevisionMonografiaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  monografiaValues?: Map<number,string>;
  administrativoValues?: Map<number,string>;
  docenteValues?: Map<number,string>;
  currentIdRevisionMonografia?: number;

  editForm = new FormGroup({
    idRevisionMonografia: new FormControl({ value: null, disabled: true }),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    fecHoraDesignacion: new FormControl(null, [Validators.required]),
    esAprobadorFinal: new FormControl(false),
    fecHoraRevision: new FormControl(null),
    estRevisionMoografia: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    monografia: new FormControl(null, [Validators.required]),
    administrativo: new FormControl(null),
    docente: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@revisionMonografia.update.success:Revision Monografia was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdRevisionMonografia = +this.route.snapshot.params['idRevisionMonografia'];
    this.revisionMonografiaService.getMonografiaValues()
        .subscribe({
          next: (data) => this.monografiaValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.revisionMonografiaService.getAdministrativoValues()
        .subscribe({
          next: (data) => this.administrativoValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.revisionMonografiaService.getDocenteValues()
        .subscribe({
          next: (data) => this.docenteValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.revisionMonografiaService.getRevisionMonografia(this.currentIdRevisionMonografia!)
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
    const data = new RevisionMonografiaDTO(this.editForm.value);
    this.revisionMonografiaService.updateRevisionMonografia(this.currentIdRevisionMonografia!, data)
        .subscribe({
          next: () => this.router.navigate(['/revisionMonografias'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
