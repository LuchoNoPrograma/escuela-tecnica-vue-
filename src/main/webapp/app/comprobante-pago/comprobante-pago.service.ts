import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ComprobantePagoDTO } from 'app/comprobante-pago/comprobante-pago.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ComprobantePagoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/comprobantePagos';

  getAllComprobantePagoes() {
    return this.http.get<ComprobantePagoDTO[]>(this.resourcePath);
  }

  getComprobantePago(idComprobante: number) {
    return this.http.get<ComprobantePagoDTO>(this.resourcePath + '/' + idComprobante);
  }

  createComprobantePago(comprobantePagoDTO: ComprobantePagoDTO) {
    return this.http.post<number>(this.resourcePath, comprobantePagoDTO);
  }

  updateComprobantePago(idComprobante: number, comprobantePagoDTO: ComprobantePagoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idComprobante, comprobantePagoDTO);
  }

  deleteComprobantePago(idComprobante: number) {
    return this.http.delete(this.resourcePath + '/' + idComprobante);
  }

  getListaDetallesPagosValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/listaDetallesPagosValues')
        .pipe(map(transformRecordToMap));
  }

}
