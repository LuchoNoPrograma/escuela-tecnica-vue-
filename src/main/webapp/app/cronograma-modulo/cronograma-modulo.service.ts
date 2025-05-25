import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CronogramaModuloDTO } from 'app/cronograma-modulo/cronograma-modulo.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class CronogramaModuloService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/cronogramaModulos';

  getAllCronogramaModuloes() {
    return this.http.get<CronogramaModuloDTO[]>(this.resourcePath);
  }

  getCronogramaModulo(idCronogramaMod: number) {
    return this.http.get<CronogramaModuloDTO>(this.resourcePath + '/' + idCronogramaMod);
  }

  createCronogramaModulo(cronogramaModuloDTO: CronogramaModuloDTO) {
    return this.http.post<number>(this.resourcePath, cronogramaModuloDTO);
  }

  updateCronogramaModulo(idCronogramaMod: number, cronogramaModuloDTO: CronogramaModuloDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idCronogramaMod, cronogramaModuloDTO);
  }

  deleteCronogramaModulo(idCronogramaMod: number) {
    return this.http.delete(this.resourcePath + '/' + idCronogramaMod);
  }

  getPlanEstudioDetalleValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/planEstudioDetalleValues')
        .pipe(map(transformRecordToMap));
  }

  getGrupoValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/grupoValues')
        .pipe(map(transformRecordToMap));
  }

  getDocenteValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/docenteValues')
        .pipe(map(transformRecordToMap));
  }

}
