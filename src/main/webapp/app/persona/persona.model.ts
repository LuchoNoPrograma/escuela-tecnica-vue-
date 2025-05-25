export class PersonaDTO {

  constructor(data:Partial<PersonaDTO>) {
    Object.assign(this, data);
  }

  idPersona?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  nombre?: string|null;
  apPaterno?: string|null;
  apMaterno?: string|null;
  ci?: string|null;
  nroCelular?: string|null;
  correo?: string|null;

}
