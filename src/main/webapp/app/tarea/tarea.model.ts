export class TareaDTO {

  constructor(data:Partial<TareaDTO>) {
    Object.assign(this, data);
  }

  idTarea?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombreTarea?: string|null;
  descripTarea?: string|null;
  estTarea?: boolean|null;
  agrupa?: number|null;

}
