import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { PlanEstudioDTO } from 'app/plan-estudio/plan-estudio.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class PlanEstudioService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/planEstudios';

  getAllPlanEstudios() {
    return this.http.get<PlanEstudioDTO[]>(this.resourcePath);
  }

  getPlanEstudio(idPlanEstudio: number) {
    return this.http.get<PlanEstudioDTO>(this.resourcePath + '/' + idPlanEstudio);
  }

  createPlanEstudio(planEstudioDTO: PlanEstudioDTO) {
    return this.http.post<number>(this.resourcePath, planEstudioDTO);
  }

  updatePlanEstudio(idPlanEstudio: number, planEstudioDTO: PlanEstudioDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idPlanEstudio, planEstudioDTO);
  }

  deletePlanEstudio(idPlanEstudio: number) {
    return this.http.delete(this.resourcePath + '/' + idPlanEstudio);
  }

  getProgramaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/programaValues')
        .pipe(map(transformRecordToMap));
  }

}
