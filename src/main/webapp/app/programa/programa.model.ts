export class ProgramaDTO {

  constructor(data:Partial<ProgramaDTO>) {
    Object.assign(this, data);
  }

  idPrograma?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombrePrograma?: string|null;
  estPrograma?: string|null;
  modalidad?: number|null;
  categoria?: number|null;

}
