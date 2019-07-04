import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Documento } from './documento.model';
import { MS_DOCUMENTOS_URL } from '../../app.apis';

@Injectable()
export class DocumentoService {

  constructor(private http: HttpClient) { }

  createOrUpdate(documento: Documento){
    if(documento.id == null){
      return this.http.post(`${MS_DOCUMENTOS_URL}/documentos/`,documento);
    }else{
      return this.http.put(`${MS_DOCUMENTOS_URL}/documentos/${documento.id}`,documento);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_DOCUMENTOS_URL}/documentos/${page}/${count}`);
  }

  findById(id: string){
    return this.http.get(`${MS_DOCUMENTOS_URL}/documentos/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_DOCUMENTOS_URL}/documentos/${id}`);
  }

  findByNome(name:string, page:number,count:number){
    return this.http.get(`${MS_DOCUMENTOS_URL}/documentos/search?nome=${name}&page=${page}&count=${count}`);
  }

}
