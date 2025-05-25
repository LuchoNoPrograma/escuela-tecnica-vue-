export class CertificadoDTO {

  constructor(data:Partial<CertificadoDTO>) {
    Object.assign(this, data);
  }

  idCertificado?: number|null;
  idUsuReg?: number|null;
  idUsuMod?: number|null;
  fechaEmision?: string|null;
  estCertificado?: string|null;
  administrativo?: number|null;
  matricula?: number|null;

}
