import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CriterioEvalDTO } from 'app/criterio-eval/criterio-eval.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class CriterioEvalService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/criterioEvals';

  getAllCriterioEvals() {
    return this.http.get<CriterioEvalDTO[]>(this.resourcePath);
  }

  getCriterioEval(idCriterioEval: number) {
    return this.http.get<CriterioEvalDTO>(this.resourcePath + '/' + idCriterioEval);
  }

  createCriterioEval(criterioEvalDTO: CriterioEvalDTO) {
    return this.http.post<number>(this.resourcePath, criterioEvalDTO);
  }

  updateCriterioEval(idCriterioEval: number, criterioEvalDTO: CriterioEvalDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idCriterioEval, criterioEvalDTO);
  }

  deleteCriterioEval(idCriterioEval: number) {
    return this.http.delete(this.resourcePath + '/' + idCriterioEval);
  }

  getCronogramaModValues() {
    return this.http.get<Record<string, string>>(this.resourcePath + '/cronogramaModValues')
        .pipe(map(transformRecordToMap));
  }

}
