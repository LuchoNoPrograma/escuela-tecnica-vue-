import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { TitulacionDTO } from 'app/titulacion/titulacion.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class TitulacionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/titulacions';

  getAllTitulacions() {
    return this.http.get<TitulacionDTO[]>(this.resourcePath);
  }

  getTitulacion(idTitulacion: number) {
    return this.http.get<TitulacionDTO>(this.resourcePath + '/' + idTitulacion);
  }

  createTitulacion(titulacionDTO: TitulacionDTO) {
    return this.http.post<number>(this.resourcePath, titulacionDTO);
  }

  updateTitulacion(idTitulacion: number, titulacionDTO: TitulacionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idTitulacion, titulacionDTO);
  }

  deleteTitulacion(idTitulacion: number) {
    return this.http.delete(this.resourcePath + '/' + idTitulacion);
  }

  getMatriculaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/matriculaValues')
        .pipe(map(transformRecordToMap));
  }

}
