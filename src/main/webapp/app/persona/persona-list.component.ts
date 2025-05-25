import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { PersonaService } from 'app/persona/persona.service';
import { PersonaDTO } from 'app/persona/persona.model';


@Component({
  selector: 'app-persona-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './persona-list.component.html'})
export class PersonaListComponent implements OnInit, OnDestroy {

  personaService = inject(PersonaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  personae?: PersonaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@persona.delete.success:Persona was removed successfully.`,
      'persona.administrativo.persona.referenced': $localize`:@@persona.administrativo.persona.referenced:This entity is still referenced by Administrativo ${details?.id} via field Persona.`,
      'persona.docente.persona.referenced': $localize`:@@persona.docente.persona.referenced:This entity is still referenced by Docente ${details?.id} via field Persona.`,
      'persona.matricula.persona.referenced': $localize`:@@persona.matricula.persona.referenced:This entity is still referenced by Matricula ${details?.id} via field Persona.`,
      'persona.usuario.persona.referenced': $localize`:@@persona.usuario.persona.referenced:This entity is still referenced by Usuario ${details?.id} via field Persona.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.personaService.getAllPersonae()
        .subscribe({
          next: (data) => this.personae = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idPersona: number) {
    if (!confirm(this.getMessage('confirm'))) {
      return;
    }
    this.personaService.deletePersona(idPersona)
        .subscribe({
          next: () => this.router.navigate(['/personas'], {
            state: {
              msgInfo: this.getMessage('deleted')
            }
          }),
          error: (error) => {
            if (error.error?.code === 'REFERENCED') {
              const messageParts = error.error.message.split(',');
              this.router.navigate(['/personas'], {
                state: {
                  msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                }
              });
              return;
            }
            this.errorHandler.handleServerError(error.error)
          }
        });
  }

}
