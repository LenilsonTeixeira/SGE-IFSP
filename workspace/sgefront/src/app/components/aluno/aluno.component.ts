import { Component, OnInit } from '@angular/core';
import { AlunoService } from './aluno.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { Aluno } from './aluno.model';
import { ResponseApi } from '../shared/response.api';
import { Curso } from '../curso/curso.model';
import { CursoService } from '../curso/curso.service';

@Component({
  selector: 'sge-aluno',
  templateUrl: './aluno.component.html',
  styleUrls: ['./aluno.component.css']
})
export class AlunoComponent implements OnInit {

constructor(
              private alunoService:AlunoService,
              private cursoService:CursoService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialogService: DialogService
  ) { }

  alunoForm: FormGroup;
  aluno: Aluno;
  alunos: Aluno[];
  cursos: Curso[];
  page:number=0;
  count:number=10;
  pages:Array<number>;
  message : {};
  classCss : {};
  totalElements: number;
  isSearch: boolean = false;
  nome: string;

  ngOnInit() {
    this.totalElements = 0;
    this.findAll(this.page,this.count);
    this.alunoForm = this.formBuilder.group({
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      email:this.formBuilder.control('',[Validators.required]),
      telefone:this.formBuilder.control('',[Validators.required]),
      prontuario:this.formBuilder.control('',[Validators.required]),
      curso:this.formBuilder.control('',[Validators.required])
      })
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }

      this.cursoService.getAll().subscribe((responseApi:ResponseApi) => {
              this.cursos = responseApi.data;
              console.log(this.cursos)
        })

        
  }

  findAll(page:number,count:number){
    this.alunoService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.alunos = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
    } , err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }



  delete(id:string){
    this.dialogService.confirm('Tem certeza que deseja realizar essa ação ?')
      .then((candelete:boolean) => {
          if(candelete){
            this.message = {};
            this.alunoService.delete(id).subscribe((responseApi:ResponseApi) => {
                this.showMessage({
                  type: 'success',
                  text: `Registro Deletado`
                });
                this.findAll(this.page,this.count);
            } , err => {
              this.showMessage({
                type: 'error',
                text: err['error']['errors'][0]
              });
            });
          }
      });
  }

  findById(id:string){
    this.alunoService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.aluno = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(aluno){
    this.alunoService.createOrUpdate(aluno).subscribe((responseApi:ResponseApi)=>{
      this.aluno = responseApi.data; 
    });
    if(aluno.id == null || aluno.id == ""){
      this.addAluno(aluno);
    }else{
      this.edit(aluno);
    }
    this.alunoForm.reset();
    this.showMessage({
      type: 'success',
      text: `Aluno ${aluno.nome} salvo com sucesso!`
    });
    

    setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  addAluno(aluno){
    this.alunos.push(aluno); 
  }

  edit(aluno){
    this.alunoForm = this.formBuilder.group({
      id:this.formBuilder.control(aluno.id),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      email:this.formBuilder.control('',[Validators.required]),
      telefone:this.formBuilder.control('',[Validators.required]),
      prontuario:this.formBuilder.control('',[Validators.required]),
      curso:this.formBuilder.control('',[Validators.required])
    })
    this.alunoForm.setValue({id:aluno.id, nome:aluno.nome, email:aluno.email, telefone:aluno.telefone, prontuario:aluno.prontuario, curso:aluno.curso});
  }

  findByNome(nome){
    if(nome ==="" ||typeof nome == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.nome = nome;
      this.alunoService.findByNome(nome,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.alunos = responseApi['data']['content'];
        console.log(this.alunos);
        this.pages = new Array(responseApi['data']['totalPages']);
    } , err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });

    }
  }

  private showMessage(message: {type: string, text: string}): void {
    this.message = message;
    this.buildClasses(message.type);
    setTimeout(() => {
      this.message = undefined;
    }, 3000);
  }

  private buildClasses(type: string): void {
    this.classCss = {
      'alert': true
    }
    this.classCss['alert-'+type] =  true;
  }

  setNextPage(event:any){
    event.preventDefault();
    if(this.page+1 < this.pages.length){
      this.page =  this.page +1;
      if(this.isSearch){
        this.findByNome(this.nome);
      }else{
        this.findAll(this.page,this.count);
      }
    }
  }

  setPreviousPage(event:any){
    event.preventDefault();
    if(this.page > 0){
      this.page =  this.page - 1;
      if(this.isSearch){
        this.findByNome(this.nome);
      }else{
        this.findAll(this.page,this.count);
      }
      
    }
  }

  setPage(i,event:any){
    event.preventDefault();
    this.page = i;
    if(this.isSearch){
      this.findByNome(this.nome);
    }else{
      this.findAll(this.page,this.count);
    }
  }

}
