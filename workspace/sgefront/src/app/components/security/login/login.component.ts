import { Component, OnInit } from '@angular/core';
import { User } from './user.model';
import { CurrentUser } from './current-user.model';
import { LoginService } from './login.service';
import { UserService } from './user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'sge-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) {
    this.loginShared = LoginService.getInstance();
   }

  ngOnInit() {
  }

  user = new User(null,"","","");
  loginShared: LoginService;
  message: string;

  login(){
    this.message = "";
    this.userService.login(this.user).subscribe((userAuthentication: CurrentUser) =>{
       this.loginShared.token = userAuthentication.token;
       this.loginShared.user = userAuthentication.user;
       this.loginShared.user.profile = this.loginShared.user.profile.substring(5);
       this.loginShared.showTemplate.emit(true);
       this.router.navigate(['/']);
    }, error=>{
       this.loginShared.token = null;
       this.loginShared.user = null;
       this.loginShared.showTemplate.emit(false);
       this.message = "Erro durante o processamento";
    });
  }
  
  cancelLogin(){
    this.message = "";
    this.user = new User(null,"","","");
    window.location.href="/login";
    window.location.reload();
  }

  getFormGroupClass(isInvalid: boolean, isDirty:boolean): {} {
    return {
      'form-group': true,
      'has-error' : isInvalid  && isDirty,
      'has-success' : !isInvalid  && isDirty
    };
  }


}
