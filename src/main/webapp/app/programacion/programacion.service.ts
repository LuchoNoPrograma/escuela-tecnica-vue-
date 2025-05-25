import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ProgramacionDTO } from 'app/programacion/programacion.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ProgramacionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/programacions';

  getAllProgramacions() {
    return this.http.get<ProgramacionDTO[]>(this.resourcePath);
  }

  getProgramacion(idProgramacion: number) {
    return this.http.get<ProgramacionDTO>(this.resourcePath + '/' + idProgramacion);
  }

  createProgramacion(programacionDTO: ProgramacionDTO) {
    return this.http.post<number>(this.resourcePath, programacionDTO);
  }

  updateProgramacion(idProgramacion: number, programacionDTO: ProgramacionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idProgramacion, programacionDTO);
  }

  deleteProgramacion(idProgramacion: number) {
    return this.http.delete(this.resourcePath + '/' + idProgramacion);
  }

  getCronogramaModuloValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/cronogramaModuloValues')
        .pipe(map(transformRecordToMap));
  }

  getMatriculaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/matriculaValues')
        .pipe(map(transformRecordToMap));
  }

}
