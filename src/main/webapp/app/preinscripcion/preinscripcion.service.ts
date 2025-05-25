import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { PreinscripcionDTO } from 'app/preinscripcion/preinscripcion.model';
import { PagedModel } from 'app/common/list-helper/pagination.component';


@Injectable({
  providedIn: 'root',
})
export class PreinscripcionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/preinscripcions';

  getAllPreinscripcions(params?: Record<string, string>) {
    return this.http.get<PagedModel<PreinscripcionDTO>>(this.resourcePath, { params });
  }

  getPreinscripcion(idPreinscripcion: number) {
    return this.http.get<PreinscripcionDTO>(this.resourcePath + '/' + idPreinscripcion);
  }

  createPreinscripcion(preinscripcionDTO: PreinscripcionDTO) {
    return this.http.post<number>(this.resourcePath, preinscripcionDTO);
  }

  updatePreinscripcion(idPreinscripcion: number, preinscripcionDTO: PreinscripcionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idPreinscripcion, preinscripcionDTO);
  }

  deletePreinscripcion(idPreinscripcion: number) {
    return this.http.delete(this.resourcePath + '/' + idPreinscripcion);
  }

}
