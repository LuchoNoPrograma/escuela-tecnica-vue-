import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CalificacionDTO } from 'app/calificacion/calificacion.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class CalificacionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/calificacions';

  getAllCalificacions() {
    return this.http.get<CalificacionDTO[]>(this.resourcePath);
  }

  getCalificacion(idCalificacion: number) {
    return this.http.get<CalificacionDTO>(this.resourcePath + '/' + idCalificacion);
  }

  createCalificacion(calificacionDTO: CalificacionDTO) {
    return this.http.post<number>(this.resourcePath, calificacionDTO);
  }

  updateCalificacion(idCalificacion: number, calificacionDTO: CalificacionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idCalificacion, calificacionDTO);
  }

  deleteCalificacion(idCalificacion: number) {
    return this.http.delete(this.resourcePath + '/' + idCalificacion);
  }

  getCriterioEvalValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/criterioEvalValues')
        .pipe(map(transformRecordToMap));
  }

  getProgramacionValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/programacionValues')
        .pipe(map(transformRecordToMap));
  }

}
