export class TitulacionDTO {

  constructor(data:Partial<TitulacionDTO>) {
    Object.assign(this, data);
  }

  idTitulacion?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  fecEmision?: string|null;
  codTitulo?: string|null;
  urlDoc?: string|null;
  matricula?: number|null;

}
