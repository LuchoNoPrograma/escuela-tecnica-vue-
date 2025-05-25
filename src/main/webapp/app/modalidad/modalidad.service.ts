import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ModalidadDTO } from 'app/modalidad/modalidad.model';


@Injectable({
  providedIn: 'root',
})
export class ModalidadService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/modalidads';

  getAllModalidads() {
    return this.http.get<ModalidadDTO[]>(this.resourcePath);
  }

  getModalidad(idModalidad: number) {
    return this.http.get<ModalidadDTO>(this.resourcePath + '/' + idModalidad);
  }

  createModalidad(modalidadDTO: ModalidadDTO) {
    return this.http.post<number>(this.resourcePath, modalidadDTO);
  }

  updateModalidad(idModalidad: number, modalidadDTO: ModalidadDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idModalidad, modalidadDTO);
  }

  deleteModalidad(idModalidad: number) {
    return this.http.delete(this.resourcePath + '/' + idModalidad);
  }

}
