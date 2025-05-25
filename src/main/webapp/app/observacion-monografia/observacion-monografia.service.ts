import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ObservacionMonografiaDTO } from 'app/observacion-monografia/observacion-monografia.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ObservacionMonografiaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/observacionMonografias';

  getAllObservacionMonografias() {
    return this.http.get<ObservacionMonografiaDTO[]>(this.resourcePath);
  }

  getObservacionMonografia(idObservacionMonografia: number) {
    return this.http.get<ObservacionMonografiaDTO>(this.resourcePath + '/' + idObservacionMonografia);
  }

  createObservacionMonografia(observacionMonografiaDTO: ObservacionMonografiaDTO) {
    return this.http.post<number>(this.resourcePath, observacionMonografiaDTO);
  }

  updateObservacionMonografia(idObservacionMonografia: number, observacionMonografiaDTO: ObservacionMonografiaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idObservacionMonografia, observacionMonografiaDTO);
  }

  deleteObservacionMonografia(idObservacionMonografia: number) {
    return this.http.delete(this.resourcePath + '/' + idObservacionMonografia);
  }

  getRevisionMonografiaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/revisionMonografiaValues')
        .pipe(map(transformRecordToMap));
  }

}
