export class MatriculaDTO {

  constructor(data:Partial<MatriculaDTO>) {
    Object.assign(this, data);
  }

  codMatricula?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  fecMatriculacion?: string|null;
  estMatricula?: string|null;
  grupo?: number|null;
  persona?: number|null;

}
