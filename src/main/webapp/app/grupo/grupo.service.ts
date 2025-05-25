import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { GrupoDTO } from 'app/grupo/grupo.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class GrupoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/grupos';

  getAllGrupoes() {
    return this.http.get<GrupoDTO[]>(this.resourcePath);
  }

  getGrupo(idGrupo: number) {
    return this.http.get<GrupoDTO>(this.resourcePath + '/' + idGrupo);
  }

  createGrupo(grupoDTO: GrupoDTO) {
    return this.http.post<number>(this.resourcePath, grupoDTO);
  }

  updateGrupo(idGrupo: number, grupoDTO: GrupoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idGrupo, grupoDTO);
  }

  deleteGrupo(idGrupo: number) {
    return this.http.delete(this.resourcePath + '/' + idGrupo);
  }

  getVersionValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/versionValues')
        .pipe(map(transformRecordToMap));
  }

  getProgramaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/programaValues')
        .pipe(map(transformRecordToMap));
  }

}
