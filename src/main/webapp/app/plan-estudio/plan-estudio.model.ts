export class PlanEstudioDTO {

  constructor(data:Partial<PlanEstudioDTO>) {
    Object.assign(this, data);
  }

  idPlanEstudio?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  anho?: number|null;
  vigente?: boolean|null;
  programa?: number|null;

}
