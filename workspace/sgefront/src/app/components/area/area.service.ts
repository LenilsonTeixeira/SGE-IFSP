import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Area } from './area.model';
import { MS_CONTRATOS_URL } from '../../app.apis';
import { Observable } from 'rxjs/Observable';
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import { ErrorHandler } from "../../app.error.handler";

@Injectable()
export class AreaService {

  constructor(private http: HttpClient) { }

  createOrUpdate(area: Area){

    if(area.id == null || area.id == ''){
      return this.http.post(`${MS_CONTRATOS_URL}/areas`,area);
    }else{
      return this.http.put(`${MS_CONTRATOS_URL}/areas/${area.id}`,area);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/areas/${page}/${count}`);
  }

  findById(id: string){
    return this.http.get(`${MS_CONTRATOS_URL}/areas/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_CONTRATOS_URL}/areas/${id}`);
  }

  findByNome(nome:string, page:number,count:number){
    return this.http.get(`${MS_CONTRATOS_URL}/areas/search?nome=${nome}&page=${page}&count=${count}`);
  }


}
