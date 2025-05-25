export class NivelDTO {

  constructor(data:Partial<NivelDTO>) {
    Object.assign(this, data);
  }

  idNivel?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreNivel?: string|null;

}
