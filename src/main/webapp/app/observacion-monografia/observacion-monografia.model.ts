export class ObservacionMonografiaDTO {

  constructor(data:Partial<ObservacionMonografiaDTO>) {
    Object.assign(this, data);
  }

  idObservacionMonografia?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  descripcion?: string|null;
  revisionMonografia?: number|null;

}
