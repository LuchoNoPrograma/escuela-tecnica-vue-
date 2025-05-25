import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { NivelDTO } from 'app/nivel/nivel.model';


@Injectable({
  providedIn: 'root',
})
export class NivelService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/nivels';

  getAllNivels() {
    return this.http.get<NivelDTO[]>(this.resourcePath);
  }

  getNivel(idNivel: number) {
    return this.http.get<NivelDTO>(this.resourcePath + '/' + idNivel);
  }

  createNivel(nivelDTO: NivelDTO) {
    return this.http.post<number>(this.resourcePath, nivelDTO);
  }

  updateNivel(idNivel: number, nivelDTO: NivelDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idNivel, nivelDTO);
  }

  deleteNivel(idNivel: number) {
    return this.http.delete(this.resourcePath + '/' + idNivel);
  }

}
