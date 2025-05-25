import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { DocenteDTO } from 'app/docente/docente.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class DocenteService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/docentes';

  getAllDocentes() {
    return this.http.get<DocenteDTO[]>(this.resourcePath);
  }

  getDocente(idDocente: number) {
    return this.http.get<DocenteDTO>(this.resourcePath + '/' + idDocente);
  }

  createDocente(docenteDTO: DocenteDTO) {
    return this.http.post<number>(this.resourcePath, docenteDTO);
  }

  updateDocente(idDocente: number, docenteDTO: DocenteDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idDocente, docenteDTO);
  }

  deleteDocente(idDocente: number) {
    return this.http.delete(this.resourcePath + '/' + idDocente);
  }

  getPersonaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/personaValues')
        .pipe(map(transformRecordToMap));
  }

}
