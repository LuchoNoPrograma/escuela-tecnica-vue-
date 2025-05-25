export class PlanPagoDTO {

  constructor(data:Partial<PlanPagoDTO>) {
    Object.assign(this, data);
  }

  idPlanPago?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  deudaTotal?: string|null;
  pagadoTotal?: string|null;
  estPlanPago?: string|null;
  concepto?: string|null;
  matricula?: number|null;

}
