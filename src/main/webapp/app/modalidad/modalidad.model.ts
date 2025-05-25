export class ModalidadDTO {

  constructor(data:Partial<ModalidadDTO>) {
    Object.assign(this, data);
  }

  idModalidad?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombre?: string|null;

}
