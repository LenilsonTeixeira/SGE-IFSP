import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { EmpresaService } from './empresa.service';
import { Empresa } from './empresa.model';
import { ResponseApi } from '../shared/response.api';

@Component({
  selector: 'sge-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})
export class EmpresaComponent implements OnInit {

  constructor(
    private empresaService: EmpresaService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private dialogService: DialogService
  ) { }

  empresaForm: FormGroup;
  empresa: Empresa;
  empresas: Empresa[];
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
    this.empresaForm = this.formBuilder.group({
      codigo: this.formBuilder.control('',[Validators.required]),
      cnpj:this.formBuilder.control('',[Validators.required]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      email:this.formBuilder.control('',[Validators.required]),
      telefone:this.formBuilder.control('',[Validators.required])})
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }
  }

  findAll(page:number,count:number){
    this.empresaService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.empresas = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
        console.log(this.pages);
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
            this.empresaService.delete(id).subscribe((responseApi:ResponseApi) => {
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
    this.empresaService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.empresa = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(empresa){
    this.empresaService.createOrUpdate(empresa).subscribe((responseApi:ResponseApi)=>{
      this.empresa = responseApi.data; 
    });
    if(empresa.id == null || empresa.id == ""){
      this.addEmpresa(empresa);
    }else{
      this.edit(empresa);
    }
    this.showMessage({
      type: 'success',
      text: `Empresa ${empresa.nome} foi salva com sucesso!`
    });
    this.empresaForm.reset();

    setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  addEmpresa(empresa){
    this.empresas.push(empresa); 
  }

  edit(empresa){
    this.empresaForm = this.formBuilder.group({
      id:this.formBuilder.control(empresa.id),
      codigo:this.formBuilder.control('',[Validators.required]),
      cnpj:this.formBuilder.control('',[Validators.required]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      email:this.formBuilder.control('',[Validators.required]),
      telefone:this.formBuilder.control('',[Validators.required])})
    this.empresaForm.setValue({id:empresa.id, codigo:empresa.codigo, cnpj:empresa.cnpj, nome:empresa.nome, email:empresa.email, telefone:empresa.telefone});
  }

  findByName(name){
    if(name ==="" ||typeof name == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.name = name;
      this.empresaService.findByNome(name,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.empresas = responseApi['data']['content'];
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
