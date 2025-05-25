export class ModuloDTO {

  constructor(data:Partial<ModuloDTO>) {
    Object.assign(this, data);
  }

  idModulo?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreMod?: string|null;
  competencia?: string|null;

}
