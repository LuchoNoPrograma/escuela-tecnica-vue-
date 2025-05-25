import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ProgramaDTO } from 'app/programa/programa.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ProgramaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/programas';

  getAllProgramas() {
    return this.http.get<ProgramaDTO[]>(this.resourcePath);
  }

  getPrograma(idPrograma: number) {
    return this.http.get<ProgramaDTO>(this.resourcePath + '/' + idPrograma);
  }

  createPrograma(programaDTO: ProgramaDTO) {
    return this.http.post<number>(this.resourcePath, programaDTO);
  }

  updatePrograma(idPrograma: number, programaDTO: ProgramaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idPrograma, programaDTO);
  }

  deletePrograma(idPrograma: number) {
    return this.http.delete(this.resourcePath + '/' + idPrograma);
  }

  getModalidadValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/modalidadValues')
        .pipe(map(transformRecordToMap));
  }

  getCategoriaValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/categoriaValues')
        .pipe(map(transformRecordToMap));
  }

}
