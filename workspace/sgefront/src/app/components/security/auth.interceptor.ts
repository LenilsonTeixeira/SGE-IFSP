import { LoginService } from "./login/login.service";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    loginShared : LoginService;
    constructor() { 
          this.loginShared = LoginService.getInstance();
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>  {
        let authRequest : any;
        if(this.loginShared.isLoggedIn()){
            authRequest = req.clone({
                setHeaders: {
                    'Authorization' : this.loginShared.token
                }
            });
            return next.handle(authRequest);
        } else {
            return next.handle(req);
        }
    }
}