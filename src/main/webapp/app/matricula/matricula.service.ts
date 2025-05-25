import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { MatriculaDTO } from 'app/matricula/matricula.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class MatriculaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/matriculas';

  getAllMatriculas() {
    return this.http.get<MatriculaDTO[]>(this.resourcePath);
  }

  getMatricula(codMatricula: number) {
    return this.http.get<MatriculaDTO>(this.resourcePath + '/' + codMatricula);
  }

  createMatricula(matriculaDTO: MatriculaDTO) {
    return this.http.post<number>(this.resourcePath, matriculaDTO);
  }

  updateMatricula(codMatricula: number, matriculaDTO: MatriculaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + codMatricula, matriculaDTO);
  }

  deleteMatricula(codMatricula: number) {
    return this.http.delete(this.resourcePath + '/' + codMatricula);
  }

  getGrupoValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/grupoValues')
        .pipe(map(transformRecordToMap));
  }

  getPersonaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/personaValues')
        .pipe(map(transformRecordToMap));
  }

}
