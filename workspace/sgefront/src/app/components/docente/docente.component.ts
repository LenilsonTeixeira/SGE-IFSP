import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { DocenteService } from './docente.service';
import { Docente } from './docente.model';
import { ResponseApi } from '../shared/response.api';

@Component({
  selector: 'sge-docente',
  templateUrl: './docente.component.html',
  styleUrls: ['./docente.component.css']
})
export class DocenteComponent implements OnInit {

  constructor(
              private docenteService:DocenteService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialogService: DialogService
  ) { }

  docenteForm: FormGroup;
  docente: Docente;
  docentes: Docente[];
  page:number=0;
  count:number=10;
  pages:Array<number>;
  message : {};
  classCss : {};
  totalElements: number;
  isSearch: boolean = false;
  name: string;

  ngOnInit() {
    this.totalElements = 0;
    this.findAll(this.page,this.count);
    this.docenteForm = this.formBuilder.group({
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      email:this.formBuilder.control('',[Validators.required])})
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }
  }

  findAll(page:number,count:number){
    this.docenteService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.docentes = responseApi['data']['content'];
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
            this.docenteService.delete(id).subscribe((responseApi:ResponseApi) => {
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
    this.docenteService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.docente = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(docente){
    this.docenteService.createOrUpdate(docente).subscribe((responseApi:ResponseApi)=>{
      this.docente = responseApi.data; 
    });
    if(docente.id == null || docente.id == ""){
      this.addDocente(docente);
    }else{
      this.edit(docente);
    }
    this.docenteForm.reset();
    this.showMessage({
      type: 'success',
      text: `Docente ${docente.nome} foi salvo com sucesso!`
    });
    

    setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  addDocente(docente){
    this.docentes.push(docente); 
  }

  edit(docente){
    this.docenteForm = this.formBuilder.group({
      id:this.formBuilder.control(docente.id),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      email:this.formBuilder.control('',[Validators.required])})
    this.docenteForm.setValue({id:docente.id, nome:docente.nome, email:docente.email});
  }

  findByName(name){
    if(name ==="" ||typeof name == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.name = name;
      this.docenteService.findByNome(name,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.docentes = responseApi['data']['content'];
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
