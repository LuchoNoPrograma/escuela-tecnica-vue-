import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CategoriaDTO } from 'app/categoria/categoria.model';


@Injectable({
  providedIn: 'root',
})
export class CategoriaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/categorias';

  getAllCategorias() {
    return this.http.get<CategoriaDTO[]>(this.resourcePath);
  }

  getCategoria(idCategoria: number) {
    return this.http.get<CategoriaDTO>(this.resourcePath + '/' + idCategoria);
  }

  createCategoria(categoriaDTO: CategoriaDTO) {
    return this.http.post<number>(this.resourcePath, categoriaDTO);
  }

  updateCategoria(idCategoria: number, categoriaDTO: CategoriaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idCategoria, categoriaDTO);
  }

  deleteCategoria(idCategoria: number) {
    return this.http.delete(this.resourcePath + '/' + idCategoria);
  }

}
