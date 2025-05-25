export class AdministrativoDTO {

  constructor(data:Partial<AdministrativoDTO>) {
    Object.assign(this, data);
  }

  idAdministrativo?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  fecIngreso?: string|null;
  fecSalida?: string|null;
  habil?: boolean|null;
  persona?: number|null;

}
