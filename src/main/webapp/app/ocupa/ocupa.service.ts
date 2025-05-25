import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { OcupaDTO } from 'app/ocupa/ocupa.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class OcupaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/ocupas';

  getAllOcupas() {
    return this.http.get<OcupaDTO[]>(this.resourcePath);
  }

  getOcupa(estOcupa: string) {
    return this.http.get<OcupaDTO>(this.resourcePath + '/' + estOcupa);
  }

  createOcupa(ocupaDTO: OcupaDTO) {
    return this.http.post<string>(this.resourcePath, ocupaDTO);
  }

  updateOcupa(estOcupa: string, ocupaDTO: OcupaDTO) {
    return this.http.put<string>(this.resourcePath + '/' + estOcupa, ocupaDTO);
  }

  deleteOcupa(estOcupa: string) {
    return this.http.delete(this.resourcePath + '/' + estOcupa);
  }

  getRolValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/rolValues')
        .pipe(map(transformRecordToMap));
  }

  getUsuarioValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/usuarioValues')
        .pipe(map(transformRecordToMap));
  }

}
