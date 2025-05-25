import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { PlanEstudioDetalleDTO } from 'app/plan-estudio-detalle/plan-estudio-detalle.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class PlanEstudioDetalleService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/planEstudioDetalles';

  getAllPlanEstudioDetalles() {
    return this.http.get<PlanEstudioDetalleDTO[]>(this.resourcePath);
  }

  getPlanEstudioDetalle(idPlanEstudioDetalle: number) {
    return this.http.get<PlanEstudioDetalleDTO>(this.resourcePath + '/' + idPlanEstudioDetalle);
  }

  createPlanEstudioDetalle(planEstudioDetalleDTO: PlanEstudioDetalleDTO) {
    return this.http.post<number>(this.resourcePath, planEstudioDetalleDTO);
  }

  updatePlanEstudioDetalle(idPlanEstudioDetalle: number, planEstudioDetalleDTO: PlanEstudioDetalleDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idPlanEstudioDetalle, planEstudioDetalleDTO);
  }

  deletePlanEstudioDetalle(idPlanEstudioDetalle: number) {
    return this.http.delete(this.resourcePath + '/' + idPlanEstudioDetalle);
  }

  getNivelValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/nivelValues')
        .pipe(map(transformRecordToMap));
  }

  getModuloValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/moduloValues')
        .pipe(map(transformRecordToMap));
  }

  getPlanEstudioValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/planEstudioValues')
        .pipe(map(transformRecordToMap));
  }

}
