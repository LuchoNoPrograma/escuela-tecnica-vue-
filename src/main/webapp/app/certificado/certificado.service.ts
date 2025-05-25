import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CertificadoDTO } from 'app/certificado/certificado.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class CertificadoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/certificados';

  getAllCertificadoes() {
    return this.http.get<CertificadoDTO[]>(this.resourcePath);
  }

  getCertificado(idCertificado: number) {
    return this.http.get<CertificadoDTO>(this.resourcePath + '/' + idCertificado);
  }

  createCertificado(certificadoDTO: CertificadoDTO) {
    return this.http.post<number>(this.resourcePath, certificadoDTO);
  }

  updateCertificado(idCertificado: number, certificadoDTO: CertificadoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idCertificado, certificadoDTO);
  }

  deleteCertificado(idCertificado: number) {
    return this.http.delete(this.resourcePath + '/' + idCertificado);
  }

  getAdministrativoValues() {
    return this.http.get<Record<string, number>>(this.resourcePath + '/administrativoValues')
        .pipe(map(transformRecordToMap));
  }

  getMatriculaValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/matriculaValues')
        .pipe(map(transformRecordToMap));
  }

}
