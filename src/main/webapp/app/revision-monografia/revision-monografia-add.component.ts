import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { RevisionMonografiaService } from 'app/revision-monografia/revision-monografia.service';
import { RevisionMonografiaDTO } from 'app/revision-monografia/revision-monografia.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-revision-monografia-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './revision-monografia-add.component.html'
})
export class RevisionMonografiaAddComponent implements OnInit {

  revisionMonografiaService = inject(RevisionMonografiaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  monografiaValues?: Map<number,string>;
  administrativoValues?: Map<number,string>;
  docenteValues?: Map<number,string>;

  addForm = new FormGroup({
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
      created: $localize`:@@revisionMonografia.create.success:Revision Monografia was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new RevisionMonografiaDTO(this.addForm.value);
    this.revisionMonografiaService.createRevisionMonografia(data)
        .subscribe({
          next: () => this.router.navigate(['/revisionMonografias'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
