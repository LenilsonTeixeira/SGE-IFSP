import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Empresa } from './empresa.model';
import { MS_CONTRATOS_URL } from '../../app.apis';

@Injectable()
export class EmpresaService {

  constructor(private http: HttpClient) { }

  createOrUpdate(empresa: Empresa){
    if(empresa.id == null){
      return this.http.post(`${MS_CONTRATOS_URL}/empresas/`,empresa);
    }else{
      return this.http.put(`${MS_CONTRATOS_URL}/empresas/${empresa.id}`,empresa);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/empresas/${page}/${count}`);
  }

  findById(id: string){
    return this.http.get(`${MS_CONTRATOS_URL}/empresas/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_CONTRATOS_URL}/empresas/${id}`);
  }

  findByNome(name:string, page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/empresas/search?nome=${name}&page=${page}&count=${count}`);
  }
}
