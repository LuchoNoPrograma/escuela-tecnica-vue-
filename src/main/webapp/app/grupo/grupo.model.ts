export class GrupoDTO {

  constructor(data:Partial<GrupoDTO>) {
    Object.assign(this, data);
  }

  idGrupo?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreGrupo?: string|null;
  estGrupo?: string|null;
  version?: number|null;
  programa?: number|null;

}
