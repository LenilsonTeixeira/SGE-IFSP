import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User } from "./user.model";
import { MS_CONTRATOS_URL } from "../../../app.apis";

@Injectable()
export class UserService{

    constructor(private http: HttpClient) { }

    login(user: User){
        return this.http.post(`${MS_CONTRATOS_URL}/user/auth`,user);
      }

}