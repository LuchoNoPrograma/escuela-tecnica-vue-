export class RevisionMonografiaDTO {

  constructor(data:Partial<RevisionMonografiaDTO>) {
    Object.assign(this, data);
  }

  idRevisionMonografia?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  fecHoraDesignacion?: string|null;
  esAprobadorFinal?: boolean|null;
  fecHoraRevision?: string|null;
  estRevisionMoografia?: string|null;
  monografia?: number|null;
  administrativo?: number|null;
  docente?: number|null;

}
