import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { MonografiaDTO } from 'app/monografia/monografia.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class MonografiaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/monografias';

  getAllMonografias() {
    return this.http.get<MonografiaDTO[]>(this.resourcePath);
  }

  getMonografia(idMonografia: number) {
    return this.http.get<MonografiaDTO>(this.resourcePath + '/' + idMonografia);
  }

  createMonografia(monografiaDTO: MonografiaDTO) {
    return this.http.post<number>(this.resourcePath, monografiaDTO);
  }

  updateMonografia(idMonografia: number, monografiaDTO: MonografiaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idMonografia, monografiaDTO);
  }

  deleteMonografia(idMonografia: number) {
    return this.http.delete(this.resourcePath + '/' + idMonografia);
  }

  getMatriculaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/matriculaValues')
        .pipe(map(transformRecordToMap));
  }

}
