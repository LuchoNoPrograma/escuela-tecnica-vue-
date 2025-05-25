import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { GrupoService } from 'app/grupo/grupo.service';
import { GrupoDTO } from 'app/grupo/grupo.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-grupo-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './grupo-add.component.html'
})
export class GrupoAddComponent implements OnInit {

  grupoService = inject(GrupoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  versionValues?: Map<number,string>;
  programaValues?: Map<number,string>;

  addForm = new FormGroup({
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    nombreGrupo: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    estGrupo: new FormControl(null, [Validators.required, Validators.maxLength(35)]),
    version: new FormControl(null),
    programa: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@grupo.create.success:Grupo was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new GrupoDTO(this.addForm.value);
    this.grupoService.createGrupo(data)
        .subscribe({
          next: () => this.router.navigate(['/grupos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
