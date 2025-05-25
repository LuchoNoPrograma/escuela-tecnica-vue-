import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { PlanPagoDTO } from 'app/plan-pago/plan-pago.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class PlanPagoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/planPagos';

  getAllPlanPagoes() {
    return this.http.get<PlanPagoDTO[]>(this.resourcePath);
  }

  getPlanPago(idPlanPago: number) {
    return this.http.get<PlanPagoDTO>(this.resourcePath + '/' + idPlanPago);
  }

  createPlanPago(planPagoDTO: PlanPagoDTO) {
    return this.http.post<number>(this.resourcePath, planPagoDTO);
  }

  updatePlanPago(idPlanPago: number, planPagoDTO: PlanPagoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idPlanPago, planPagoDTO);
  }

  deletePlanPago(idPlanPago: number) {
    return this.http.delete(this.resourcePath + '/' + idPlanPago);
  }

  getMatriculaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/matriculaValues')
        .pipe(map(transformRecordToMap));
  }

}
