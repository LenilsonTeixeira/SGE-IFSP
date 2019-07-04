import { Component, OnInit } from '@angular/core';
import { AtividadeService } from './atividade.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { Atividade } from './atividade.model';
import { ResponseApi } from '../shared/response.api';
import { Agrupada } from './agrupada.model';

@Component({
  selector: 'sge-atividade',
  templateUrl: './atividade.component.html',
  styleUrls: ['./atividade.component.css']
})
export class AtividadeComponent implements OnInit {

constructor(
              private atividadeService: AtividadeService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialogService: DialogService
             ) { }

  atividadeForm: FormGroup;
  atividade: Atividade;
  atividades: Atividade[];
  agrupadas: Agrupada[];
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
    this.getAggregateActivities();
    this.atividadeForm = this.formBuilder.group({
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(1), Validators.maxLength(200)])})
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }
    }

  findAll(page:number,count:number){
    this.atividadeService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.atividades = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
    } , err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }

  delete(id:string){
    this.dialogService.confirm('Tem certeza que deseja deletar essa atividade ?')
      .then((candelete:boolean) => {
          if(candelete){
            this.message = {};
            this.atividadeService.delete(id).subscribe((responseApi:ResponseApi) => {
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
    this.atividadeService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.atividade = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(atividade){
    this.atividadeService.createOrUpdate(atividade).subscribe((responseApi:ResponseApi)=>{
      this.atividade = responseApi.data; 
    });
    if(atividade.id == null || atividade.id == ""){
      this.addAtividade(atividade);
    }else{
      this.edit(atividade);
    }
    this.showMessage({
      type: 'success',
      text: `Atividade ${atividade.nome} foi salva com sucesso!`
    });
    this.atividadeForm.reset();

    setTimeout(()=>{ 
      this.findAll(this.page,this.count);
      this.getAggregateActivities();
     }, 2000);
  }

  addAtividade(atividade){
    this.atividades.push(atividade); 
  }

  edit(atividade){
    this.atividadeForm = this.formBuilder.group({
      id:this.formBuilder.control(atividade.id),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(1), Validators.maxLength(200)])})
    this.atividadeForm.setValue({id:atividade.id, nome:atividade.nome});
  }

  findByName(name){
    if(name ==="" ||typeof name == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.name = name;
      this.atividadeService.findByNome(name,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.atividades = responseApi['data']['content'];
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

  getAggregateActivities(){
    this.atividadeService.getAggregateActivities().subscribe((responseApi:ResponseApi) => {
      this.agrupadas = responseApi['data'];
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
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
