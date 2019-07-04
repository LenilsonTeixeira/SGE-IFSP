import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Curso } from './curso.model';
import { MS_CONTRATOS_URL } from '../../app.apis';

@Injectable()
export class CursoService {

  constructor(private http: HttpClient) { }

  createOrUpdate(curso: Curso){

    if(curso.id == null){
      return this.http.post(`${MS_CONTRATOS_URL}/cursos`,curso);
    }else{
      return this.http.put(`${MS_CONTRATOS_URL}/cursos/${curso.id}`,curso);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/cursos/${page}/${count}`);
  }

  getAll(){
    return this.http.get(`${MS_CONTRATOS_URL}/cursos/`);
  }

  findById(id: string){
    return this.http.get(`${MS_CONTRATOS_URL}/cursos/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_CONTRATOS_URL}/cursos/${id}`);
  }

  findByNome(nome:string, page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/cursos/search?nome=${nome}&page=${page}&count=${count}`);
  }

}
