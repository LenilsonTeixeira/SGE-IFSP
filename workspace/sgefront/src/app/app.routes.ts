import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AreaComponent } from './components/area/area.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { ModuleWithProviders } from '@angular/core';
import { LoginComponent } from './components/security/login/login.component';
import { AuthGuard } from './components/security/auth.guard';
import { DocenteComponent } from './components/docente/docente.component';
import { EmpresaComponent } from './components/empresa/empresa.component';
import { DocumentoComponent } from './components/documento/documento.component';
import { CursoComponent } from './components/curso/curso.component';
import { AtividadeComponent } from './components/atividade/atividade.component';
import { AlunoComponent } from './components/aluno/aluno.component';
import { ContratoComponent } from './components/contrato/contrato.component';

export const ROUTES: Routes = [
    {path:'', component:DashboardComponent},
    {path:'dashboard', component:DashboardComponent},
    {path:'areas', component:AreaComponent},
    {path:'alunos', component:AlunoComponent},
    {path:'atividades', component:AtividadeComponent},
    {path:'usuarios', component:UsuarioComponent},
    {path:'contratos', component:ContratoComponent},
    {path:'docentes', component:DocenteComponent}, 
    {path:'documentos', component:DocumentoComponent},
    {path:'empresas', component:EmpresaComponent},
    {path:'cursos', component:CursoComponent},
    {path:'login', component:LoginComponent}
]
export const routes: ModuleWithProviders = RouterModule.forRoot(ROUTES);