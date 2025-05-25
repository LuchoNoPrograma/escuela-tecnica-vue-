export class DetallePagoDTO {

  constructor(data:Partial<DetallePagoDTO>) {
    Object.assign(this, data);
  }

  idDetallePago?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  montoPagadoDetalle?: string|null;
  planPago?: number|null;

}
