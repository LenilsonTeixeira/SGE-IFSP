import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contrato } from './contrato.model';
import { MS_CONTRATOS_URL } from '../../app.apis';

@Injectable()
export class ContratoService {

  constructor(private http: HttpClient) { }

  create(contrato: Contrato){
      return this.http.post(`${MS_CONTRATOS_URL}/contratos/`,contrato);
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/contratos/${page}/${count}`);
  }

  getAll(){
    return this.http.get(`${MS_CONTRATOS_URL}/contratos/`);
  }

  findById(id: string){
    return this.http.get(`${MS_CONTRATOS_URL}/contratos/${id}`);
  }

  delete(id: number){
    return this.http.delete(`${MS_CONTRATOS_URL}/contratos/${id}`);
  }

  fillStudent(prontuario:string){
    return this.http.get(`${MS_CONTRATOS_URL}/alunos/prontuario?prontuario=${prontuario}`);
  }

  fillCompany(codigo:number){
    return this.http.get(`${MS_CONTRATOS_URL}/empresas/codigo?codigo=${codigo}`);
  }

  alunoFindById(id:number){
    return this.http.get(`${MS_CONTRATOS_URL}/alunos/${id}`);
  }

  empresaFindById(id:number){
    return this.http.get(`${MS_CONTRATOS_URL}/empresas/${id}`);
  }

}
