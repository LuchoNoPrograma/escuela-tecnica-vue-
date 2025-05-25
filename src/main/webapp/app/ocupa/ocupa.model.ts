export class OcupaDTO {

  constructor(data:Partial<OcupaDTO>) {
    Object.assign(this, data);
  }

  estOcupa?: string|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  rol?: number|null;
  usuario?: number|null;

}
