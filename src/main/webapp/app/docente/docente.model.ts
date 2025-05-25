export class DocenteDTO {

  constructor(data:Partial<DocenteDTO>) {
    Object.assign(this, data);
  }

  idDocente?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nroResolucion?: number|null;
  fecInicioContrato?: string|null;
  fecFinContrato?: string|null;
  persona?: number|null;

}
