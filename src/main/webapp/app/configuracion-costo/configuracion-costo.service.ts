import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ConfiguracionCostoDTO } from 'app/configuracion-costo/configuracion-costo.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ConfiguracionCostoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/configuracionCostos';

  getAllConfiguracionCostoes() {
    return this.http.get<ConfiguracionCostoDTO[]>(this.resourcePath);
  }

  getConfiguracionCosto(idConfiguracionCosto: number) {
    return this.http.get<ConfiguracionCostoDTO>(this.resourcePath + '/' + idConfiguracionCosto);
  }

  createConfiguracionCosto(configuracionCostoDTO: ConfiguracionCostoDTO) {
    return this.http.post<number>(this.resourcePath, configuracionCostoDTO);
  }

  updateConfiguracionCosto(idConfiguracionCosto: number, configuracionCostoDTO: ConfiguracionCostoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idConfiguracionCosto, configuracionCostoDTO);
  }

  deleteConfiguracionCosto(idConfiguracionCosto: number) {
    return this.http.delete(this.resourcePath + '/' + idConfiguracionCosto);
  }

  getProgramaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/programaValues')
        .pipe(map(transformRecordToMap));
  }

}
