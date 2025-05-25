export class ConfiguracionCostoDTO {

  constructor(data:Partial<ConfiguracionCostoDTO>) {
    Object.assign(this, data);
  }

  idConfiguracionCosto?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  montoConfig?: string|null;
  fecInicioVig?: string|null;
  fecFinVig?: string|null;
  estConfig?: boolean|null;
  concepto?: string|null;
  programa?: number|null;

}
