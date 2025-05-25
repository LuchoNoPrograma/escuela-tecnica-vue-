import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { TareaDTO } from 'app/tarea/tarea.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class TareaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/tareas';

  getAllTareas() {
    return this.http.get<TareaDTO[]>(this.resourcePath);
  }

  getTarea(idTarea: number) {
    return this.http.get<TareaDTO>(this.resourcePath + '/' + idTarea);
  }

  createTarea(tareaDTO: TareaDTO) {
    return this.http.post<number>(this.resourcePath, tareaDTO);
  }

  updateTarea(idTarea: number, tareaDTO: TareaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idTarea, tareaDTO);
  }

  deleteTarea(idTarea: number) {
    return this.http.delete(this.resourcePath + '/' + idTarea);
  }

  getAgrupaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/agrupaValues')
        .pipe(map(transformRecordToMap));
  }

}
