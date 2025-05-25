export class PreinscripcionDTO {

  constructor(data:Partial<PreinscripcionDTO>) {
    Object.assign(this, data);
  }

  idPreinscripcion?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombres?: string|null;
  paterno?: string|null;
  materno?: string|null;
  ci?: string|null;
  celular?: string|null;

}
