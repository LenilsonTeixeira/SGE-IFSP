import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { MenuLateralComponent } from './components/layout/menu-lateral/menu-lateral.component';
import { RodapeComponent } from './components/layout/rodape/rodape.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { AreaComponent } from './components/area/area.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/security/login/login.component';
import { routes } from './app.routes';
import { UserService } from './components/security/login/user.service';
import { LoginService } from './components/security/login/login.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthInterceptor } from './components/security/auth.interceptor';
import { AuthGuard } from './components/security/auth.guard';
import { AreaService } from './components/area/area.service';
import { ContentHeaderComponent } from './components/shared/content-header/content-header.component';
import { InputComponent } from './components/shared/input/input.component';
import { SettingsComponent } from './components/layout/settings/settings.component';
import { DialogService } from './components/shared/dialog.service';
import { DocenteComponent } from './components/docente/docente.component';
import { DocenteService } from './components/docente/docente.service';
import { EmpresaComponent } from './components/empresa/empresa.component';
import { EmpresaService } from './components/empresa/empresa.service';
import { CursoComponent } from './components/curso/curso.component';
import { DocumentoComponent } from './components/documento/documento.component';
import { DocumentoService } from './components/documento/documento.service';
import { AlunoComponent } from './components/aluno/aluno.component';
import { CursoService } from './components/curso/curso.service';
import { AtividadeComponent } from './components/atividade/atividade.component';
import { AtividadeService } from './components/atividade/atividade.service';
import { AlunoService } from './components/aluno/aluno.service';
import { DashboardService } from './components/dashboard/dashboard.service';
import { ContratoComponent } from './components/contrato/contrato.component';
import { ContratoService } from './components/contrato/contrato.service';
import { UppercaseDirective } from './components/shared/uppercase.directive';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuLateralComponent,
    RodapeComponent,
    UsuarioComponent,
    AreaComponent,
    DashboardComponent,
    LoginComponent,
    ContentHeaderComponent,
    InputComponent,
    SettingsComponent,
    DocenteComponent,
    EmpresaComponent,
    CursoComponent,
    DocumentoComponent,
    AlunoComponent,
    AtividadeComponent,
    ContratoComponent,
    UppercaseDirective
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    routes
  ],
  providers: [
    DashboardService,
    UserService,
    LoginService,
    AreaService,
    AlunoService,
    CursoService,
    AtividadeService,
    DocenteService,
    EmpresaService,
    DocumentoService,
    ContratoService,
    DialogService,
    AuthGuard
    ,
    { provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
