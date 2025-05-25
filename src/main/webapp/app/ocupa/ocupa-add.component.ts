import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { OcupaService } from 'app/ocupa/ocupa.service';
import { OcupaDTO } from 'app/ocupa/ocupa.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-ocupa-add',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './ocupa-add.component.html'
})
export class OcupaAddComponent implements OnInit {

  ocupaService = inject(OcupaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  rolValues?: Map<number,string>;
  usuarioValues?: Map<number,string>;

  addForm = new FormGroup({
    estOcupa: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    idUsuReg: new FormControl(null, [Validators.required]),
    idUsuMod: new FormControl(null),
    rol: new FormControl(null, [Validators.required]),
    usuario: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@ocupa.create.success:Ocupa was created successfully.`,
      OCUPA_EST_OCUPA_VALID: $localize`:@@Exists.ocupa.estOcupa:This Est Ocupa is already taken.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.ocupaService.getRolValues()
        .subscribe({
          next: (data) => this.rolValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.ocupaService.getUsuarioValues()
        .subscribe({
          next: (data) => this.usuarioValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new OcupaDTO(this.addForm.value);
    this.ocupaService.createOcupa(data)
        .subscribe({
          next: () => this.router.navigate(['/ocupas'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
