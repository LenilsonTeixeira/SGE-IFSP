import { Component } from '@angular/core';
import { LoginService } from './components/security/login/login.service';
import { LayoutModule } from 'angular-admin-lte';    //Loading layout module
import { BoxModule } from 'angular-admin-lte';       //Box component
@Component({
  selector: 'sge-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  showTemplate: boolean = true;
  public loginShared: LoginService;

  constructor(){
    this.loginShared = LoginService.getInstance();
  }

  ngOnInit(){
    this.loginShared.showTemplate.subscribe(show=> this.showTemplate = show);
  }

  showContentWrapper(){
    return {
      //'content-wrapper': this.loginShared.isLoggedIn()
      'content-wrapper': true
    }
  }


  
}


