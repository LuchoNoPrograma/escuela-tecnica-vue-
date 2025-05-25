export class CategoriaDTO {

  constructor(data:Partial<CategoriaDTO>) {
    Object.assign(this, data);
  }

  idCategoria?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreCat?: string|null;

}
