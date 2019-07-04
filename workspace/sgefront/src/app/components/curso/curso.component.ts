import { Component, OnInit } from '@angular/core';
import { CursoService } from './curso.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { Curso } from './curso.model';
import { ResponseApi } from '../shared/response.api';

@Component({
  selector: 'sge-curso',
  templateUrl: './curso.component.html',
  styleUrls: ['./curso.component.css']
})
export class CursoComponent implements OnInit {

constructor(
              private cursoService: CursoService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialogService: DialogService
             ) { }

  cursoForm: FormGroup;
  curso: Curso;
  cursos: Curso[];
  page:number=0;
  count:number=10;
  pages:Array<number>;
  message : {};
  classCss : {};
  totalElements: number;
  isSearch: boolean = false;
  name: string;


  ngOnInit() {
    this.findAll(this.page,this.count);
    this.cursoForm = this.formBuilder.group({
      sigla:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)])})
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }
    }

  findAll(page:number,count:number){
    this.cursoService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.cursos = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
    } , err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });

  }

  delete(id:string){
    this.dialogService.confirm('Tem certeza que deseja deletar esse curso ?')
      .then((candelete:boolean) => {
          if(candelete){
            this.message = {};
            this.cursoService.delete(id).subscribe((responseApi:ResponseApi) => {
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
    this.cursoService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.curso = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(curso){
    this.cursoService.createOrUpdate(curso).subscribe((responseApi:ResponseApi)=>{
      this.curso = responseApi.data; 
    });
    if(curso.id == null || curso.id == ""){
      this.addCurso(curso);
    }else{
      this.edit(curso);
    }
    this.showMessage({
      type: 'success',
      text: `Curso ${curso.nome} foi salvo com sucesso!`
    });
    this.cursoForm.reset();

    setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  addCurso(curso){
    this.cursos.push(curso); 
  }

  edit(curso){
    this.cursoForm = this.formBuilder.group({
      id:this.formBuilder.control(curso.id),
      sigla:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)])})
    this.cursoForm.setValue({id:curso.id, sigla:curso.sigla, nome:curso.nome});
    console.log(this.cursoForm);
  }

  findByName(name){
    if(name ==="" ||typeof name == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.name = name;
      this.cursoService.findByNome(name,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.cursos = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
        console.log(this.pages);
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
        this.findByName(this.name);
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
        this.findByName(this.name);
      }else{
        this.findAll(this.page,this.count);
      }
      
    }
  }

  setPage(i,event:any){
    event.preventDefault();
    this.page = i;
    if(this.isSearch){
      this.findByName(this.name);
    }else{
      this.findAll(this.page,this.count);
    }
  }

}
