import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from './usuario.model';
import { MS_CONTRATOS_URL } from '../../app.apis';

@Injectable()
export class UsuarioService {

  constructor(private http: HttpClient) { }

  createOrUpdate(usuario: Usuario){
    if(usuario.id != null && usuario.id != ''){
      return this.http.put(`${MS_CONTRATOS_URL}/usuarios`,usuario);
    }else{
      usuario.id = null;
      return this.http.post(`${MS_CONTRATOS_URL}/usuarios`,usuario);
    }
  }

  findAll(page: number, count: number){
    this.http.get(`${MS_CONTRATOS_URL}/usuarios/${page}/${count}`);
  }

  findById(id: string){
    this.http.get(`${MS_CONTRATOS_URL}/usuarios/${id}`);
  }

  delete(id: string){
    this.http.delete(`${MS_CONTRATOS_URL}/usuarios/${id}`);
  }

}
