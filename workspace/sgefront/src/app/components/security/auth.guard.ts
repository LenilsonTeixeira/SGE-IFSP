import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Injectable } from "@angular/core";
import { LoginService } from "./login/login.service";
import { UserService } from "./login/user.service";
import { Observable } from "rxjs/Observable";

@Injectable()
export class AuthGuard implements CanActivate {

  public loginShared: LoginService;
  
  constructor(private userService: UserService,
              private router: Router) { 
                this.loginShared = LoginService.getInstance();
  }
  
  canActivate(
      route: ActivatedRouteSnapshot, 
      state: RouterStateSnapshot): Observable<boolean> | boolean {
    if(this.loginShared.isLoggedIn()){
        return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}