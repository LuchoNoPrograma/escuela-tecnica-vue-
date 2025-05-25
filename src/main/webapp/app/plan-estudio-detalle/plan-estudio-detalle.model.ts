export class PlanEstudioDetalleDTO {

  constructor(data:Partial<PlanEstudioDetalleDTO>) {
    Object.assign(this, data);
  }

  idPlanEstudioDetalle?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  cargaHoraria?: number|null;
  creditos?: string|null;
  orden?: number|null;
  sigla?: string|null;
  nivel?: number|null;
  modulo?: number|null;
  planEstudio?: number|null;

}
