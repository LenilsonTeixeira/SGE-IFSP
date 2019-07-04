import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Docente } from './docente.model';
import { MS_DOCENTES_URL } from '../../app.apis';

@Injectable()
export class DocenteService {

  constructor(private http: HttpClient) { }

  createOrUpdate(docente: Docente){
    if(docente.id == "" || docente.id == null){
      return this.http.post(`${MS_DOCENTES_URL}/docentes/`,docente);
    }else{
      return this.http.put(`${MS_DOCENTES_URL}/docentes/${docente.id}`,docente);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_DOCENTES_URL}/docentes/${page}/${count}`);
  }

  findById(id: string){
    return this.http.get(`${MS_DOCENTES_URL}/docentes/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_DOCENTES_URL}/docentes/${id}`);
  }

  findByNome(name:string, page:number,count:number){
    return this.http.get(`${MS_DOCENTES_URL}/docentes/search?nome=${name}&page=${page}&count=${count}`);
  }

}
