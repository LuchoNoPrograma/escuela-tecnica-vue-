export class MonografiaDTO {

  constructor(data:Partial<MonografiaDTO>) {
    Object.assign(this, data);
  }

  idMonografia?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  titulo?: string|null;
  estMonografia?: string|null;
  notaFinal?: number|null;
  matricula?: number|null;

}
