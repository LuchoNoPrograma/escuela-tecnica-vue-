import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { DetallePagoDTO } from 'app/detalle-pago/detalle-pago.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class DetallePagoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/detallePagos';

  getAllDetallePagoes() {
    return this.http.get<DetallePagoDTO[]>(this.resourcePath);
  }

  getDetallePago(idDetallePago: number) {
    return this.http.get<DetallePagoDTO>(this.resourcePath + '/' + idDetallePago);
  }

  createDetallePago(detallePagoDTO: DetallePagoDTO) {
    return this.http.post<number>(this.resourcePath, detallePagoDTO);
  }

  updateDetallePago(idDetallePago: number, detallePagoDTO: DetallePagoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idDetallePago, detallePagoDTO);
  }

  deleteDetallePago(idDetallePago: number) {
    return this.http.delete(this.resourcePath + '/' + idDetallePago);
  }

  getPlanPagoValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/planPagoValues')
        .pipe(map(transformRecordToMap));
  }

}
