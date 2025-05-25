export class RolDTO {

  constructor(data:Partial<RolDTO>) {
    Object.assign(this, data);
  }

  idPermiso?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombre?: string|null;
  estCargo?: string|null;
  descripcionCargo?: string|null;

}
