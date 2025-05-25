export class ComprobantePagoDTO {

  constructor(data:Partial<ComprobantePagoDTO>) {
    Object.assign(this, data);
  }

  idComprobante?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  codComprobante?: string|null;
  monto?: string|null;
  fecComprobante?: string|null;
  tipoDeposito?: string|null;
  listaDetallesPagos?: number[]|null;

}
