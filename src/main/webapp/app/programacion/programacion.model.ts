export class ProgramacionDTO {

  constructor(data:Partial<ProgramacionDTO>) {
    Object.assign(this, data);
  }

  idProgramacion?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  observacion?: string|null;
  fecProgramacion?: string|null;
  estCalificacion?: string|null;
  tipoProgramacion?: string|null;
  calificacionFinal?: number|null;
  cronogramaModulo?: number|null;
  matricula?: number|null;

}
