export class CalificacionDTO {

  constructor(data:Partial<CalificacionDTO>) {
    Object.assign(this, data);
  }

  idCalificacion?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nota?: string|null;
  criterioEval?: number|null;
  programacion?: number|null;

}
