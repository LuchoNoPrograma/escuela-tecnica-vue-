import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ModuloDTO } from 'app/modulo/modulo.model';


@Injectable({
  providedIn: 'root',
})
export class ModuloService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/modulos';

  getAllModuloes() {
    return this.http.get<ModuloDTO[]>(this.resourcePath);
  }

  getModulo(idModulo: number) {
    return this.http.get<ModuloDTO>(this.resourcePath + '/' + idModulo);
  }

  createModulo(moduloDTO: ModuloDTO) {
    return this.http.post<number>(this.resourcePath, moduloDTO);
  }

  updateModulo(idModulo: number, moduloDTO: ModuloDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idModulo, moduloDTO);
  }

  deleteModulo(idModulo: number) {
    return this.http.delete(this.resourcePath + '/' + idModulo);
  }

}
