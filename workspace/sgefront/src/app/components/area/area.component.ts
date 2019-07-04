import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Area } from './area.model';
import { AreaService } from './area.service';
import { LoginService } from '../security/login/login.service';
import { ActivatedRoute } from '@angular/router';
import { ResponseApi } from '../shared/response.api';
import { Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';

@Component({
  selector: 'sge-area',
  templateUrl: './area.component.html',
  styleUrls: ['./area.component.css']
})
export class AreaComponent implements OnInit {
  constructor(
              private areaService: AreaService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialogService: DialogService
             ) { }

  areaForm: FormGroup;
  area: Area;
  areas: Area[];
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
    this.areaForm = this.formBuilder.group({
      sigla:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(45)])})
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }
    }

  findAll(page:number,count:number){
    this.areaService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.areas = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
    } , err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });

  }

  delete(id:string){
    this.dialogService.confirm('Tem certeza que deseja deletar essa Área ?')
      .then((candelete:boolean) => {
          if(candelete){
            this.message = {};
            this.areaService.delete(id).subscribe((responseApi:ResponseApi) => {
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
    this.areaService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.area = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(area){
    this.areaService.createOrUpdate(area).subscribe((responseApi:ResponseApi)=>{
      this.area = responseApi.data; 
    });
    if(area.id == null || area.id == ""){
      this.addArea(area);
    }else{
      this.edit(area);
    }
    this.showMessage({
      type: 'success',
      text: `Área ${area.nome} foi salva com sucesso!`
    });
    this.areaForm.reset();

    setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  addArea(area){
    this.areas.push(area); 
  }

  edit(area){
    this.areaForm = this.formBuilder.group({
      id:this.formBuilder.control(area.id),
      sigla:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(45)])})
    this.areaForm.setValue({id:area.id, sigla:area.sigla, nome:area.nome});
  }

  findByName(name){
    if(name ==="" ||typeof name == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.name = name;
      this.areaService.findByNome(name,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.areas = responseApi['data']['content'];
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
