export class CriterioEvalDTO {

  constructor(data:Partial<CriterioEvalDTO>) {
    Object.assign(this, data);
  }

  idCriterioEval?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreCrit?: string|null;
  descripcion?: string|null;
  ponderacion?: number|null;
  orden?: number|null;
  cronogramaMod?: number|null;

}
