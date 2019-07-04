import { Injectable, EventEmitter } from '@angular/core';
import { User } from './user.model';


@Injectable()
export class LoginService {

  public static instance: LoginService = null;
  user: User;
  token: string;
  showTemplate = new EventEmitter<boolean>();

  constructor() { 
    return LoginService.instance = LoginService.instance || this;
  }

  public static getInstance(){
    if(this.instance == null){
      return this.instance = new LoginService();
    }
    return this.instance;
  }

  isLoggedIn():boolean{
    if(this.user == null){
      return false;
    }
      return this.user.email != '';

  }

}
