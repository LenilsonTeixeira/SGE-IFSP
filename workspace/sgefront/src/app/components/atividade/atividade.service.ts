import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Atividade } from './atividade.model';
import { MS_ATIVIDADES_URL } from '../../app.apis';

@Injectable()
export class AtividadeService {

  constructor(private http: HttpClient) { }

  createOrUpdate(atividade: Atividade){

    if(atividade.id == null || atividade.id == ''){
      return this.http.post(`${MS_ATIVIDADES_URL}/atividades`,atividade);
    }else{
      return this.http.put(`${MS_ATIVIDADES_URL}/atividades/${atividade.id}`,atividade);
    }
  }

  findAll(page:number,count:number){
    return this.http.get(`${MS_ATIVIDADES_URL}/atividades/${page}/${count}`);
  }

  findById(id: string){
    return this.http.get(`${MS_ATIVIDADES_URL}/atividades/${id}`);
  }

  delete(id: string){
    return this.http.delete(`${MS_ATIVIDADES_URL}/atividades/${id}`);
  }

  findByNome(nome:string, page:number,count:number){
    return this.http.get(`${MS_ATIVIDADES_URL}/atividades/search?nome=${nome}&page=${page}&count=${count}`);
  }

  getAggregateActivities(){
    return this.http.get(`${MS_ATIVIDADES_URL}/atividades/agrupadas`);
  }

}
