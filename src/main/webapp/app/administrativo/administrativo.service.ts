import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { AdministrativoDTO } from 'app/administrativo/administrativo.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class AdministrativoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/administrativos';

  getAllAdministrativoes() {
    return this.http.get<AdministrativoDTO[]>(this.resourcePath);
  }

  getAdministrativo(idAdministrativo: number) {
    return this.http.get<AdministrativoDTO>(this.resourcePath + '/' + idAdministrativo);
  }

  createAdministrativo(administrativoDTO: AdministrativoDTO) {
    return this.http.post<number>(this.resourcePath, administrativoDTO);
  }

  updateAdministrativo(idAdministrativo: number, administrativoDTO: AdministrativoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idAdministrativo, administrativoDTO);
  }

  deleteAdministrativo(idAdministrativo: number) {
    return this.http.delete(this.resourcePath + '/' + idAdministrativo);
  }

  getPersonaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/personaValues')
        .pipe(map(transformRecordToMap));
  }

}
