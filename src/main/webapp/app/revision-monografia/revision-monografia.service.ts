import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { RevisionMonografiaDTO } from 'app/revision-monografia/revision-monografia.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class RevisionMonografiaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/revisionMonografias';

  getAllRevisionMonografias() {
    return this.http.get<RevisionMonografiaDTO[]>(this.resourcePath);
  }

  getRevisionMonografia(idRevisionMonografia: number) {
    return this.http.get<RevisionMonografiaDTO>(this.resourcePath + '/' + idRevisionMonografia);
  }

  createRevisionMonografia(revisionMonografiaDTO: RevisionMonografiaDTO) {
    return this.http.post<number>(this.resourcePath, revisionMonografiaDTO);
  }

  updateRevisionMonografia(idRevisionMonografia: number, revisionMonografiaDTO: RevisionMonografiaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idRevisionMonografia, revisionMonografiaDTO);
  }

  deleteRevisionMonografia(idRevisionMonografia: number) {
    return this.http.delete(this.resourcePath + '/' + idRevisionMonografia);
  }

  getMonografiaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/monografiaValues')
        .pipe(map(transformRecordToMap));
  }

  getAdministrativoValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/administrativoValues')
        .pipe(map(transformRecordToMap));
  }

  getDocenteValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/docenteValues')
        .pipe(map(transformRecordToMap));
  }

}
