export class CronogramaModuloDTO {

  constructor(data:Partial<CronogramaModuloDTO>) {
    Object.assign(this, data);
  }

  idCronogramaMod?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  fecInicio?: string|null;
  fecFin?: string|null;
  estado?: string|null;
  planEstudioDetalle?: number|null;
  grupo?: number|null;
  docente?: number|null;

}
