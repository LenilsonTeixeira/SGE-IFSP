import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Aluno } from './aluno.model';
import { MS_CONTRATOS_URL } from '../../app.apis';

@Injectable()
export class AlunoService {

  constructor(private http: HttpClient) { }

  createOrUpdate(aluno: Aluno){
    if(aluno.id == null){
      return this.http.post(`${MS_CONTRATOS_URL}/alunos/`,aluno);
    }else{
      return this.http.put(`${MS_CONTRATOS_URL}/alunos/${aluno.id}`,aluno);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/alunos/${page}/${count}`);
  }

  getAll(){
    return this.http.get(`${MS_CONTRATOS_URL}/alunos/`);
  }

  findById(id: string){
    return this.http.get(`${MS_CONTRATOS_URL}/alunos/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_CONTRATOS_URL}/alunos/${id}`);
  }

  findByNome(nome:string, page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/alunos/search?nome=${nome}&page=${page}&count=${count}`);
  }

}
