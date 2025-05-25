import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { PersonaDTO } from 'app/persona/persona.model';


@Injectable({
  providedIn: 'root',
})
export class PersonaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/personas';

  getAllPersonae() {
    return this.http.get<PersonaDTO[]>(this.resourcePath);
  }

  getPersona(idPersona: number) {
    return this.http.get<PersonaDTO>(this.resourcePath + '/' + idPersona);
  }

  createPersona(personaDTO: PersonaDTO) {
    return this.http.post<number>(this.resourcePath, personaDTO);
  }

  updatePersona(idPersona: number, personaDTO: PersonaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idPersona, personaDTO);
  }

  deletePersona(idPersona: number) {
    return this.http.delete(this.resourcePath + '/' + idPersona);
  }

}
