import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { DocumentoService } from './documento.service';
import { Documento} from './documento.model';
import { ResponseApi } from '../shared/response.api';

@Component({
  selector: 'sge-documento',
  templateUrl: './documento.component.html',
  styleUrls: ['./documento.component.css']
})
export class DocumentoComponent implements OnInit {

  constructor(
    private documentoService: DocumentoService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private dialogService: DialogService
  ) { }

  documentoForm: FormGroup;
  documento: Documento;
  documentos: Documento[];
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
    this.documentoForm = this.formBuilder.group({
      sigla:this.formBuilder.control('',[Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      copias:this.formBuilder.control('',[Validators.required]),
    })
      let id:string = this.route.snapshot.params['id'];
      if(id != undefined){
        this.findById(id);
      }
  }

  findAll(page:number,count:number){
    this.documentoService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.documentos = responseApi['data']['content'];
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
            this.documentoService.delete(id).subscribe((responseApi:ResponseApi) => {
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
    this.documentoService.findById(id).subscribe((responseApi:ResponseApi) => {
      this.documento = responseApi.data;
  } , err => {
    this.showMessage({
      type: 'error',
      text: err['error']['errors'][0]
    });
  });
  }

  createOrUpdate(documento){
    console.log(this.documentoForm.value)
    this.documentoService.createOrUpdate(documento).subscribe((responseApi:ResponseApi)=>{
      this.documento = responseApi.data; 
    });
    if(documento.id == null || documento.id == ""){
      this.addDocumento(documento);
    }else{
      this.edit(documento);
    }
    this.showMessage({
      type: 'success',
      text: `Documento ${documento.nome} foi salvo com sucesso!`
    });
    this.documentoForm.reset();

    setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  addDocumento(documento){
    this.documentos.push(documento); 
  }

  edit(documento){
    this.documentoForm = this.formBuilder.group({
      id:this.formBuilder.control(documento.id),
      sigla:this.formBuilder.control('',[Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      nome:this.formBuilder.control('',[Validators.required, Validators.minLength(3), Validators.maxLength(200)]),
      copias:this.formBuilder.control('',[Validators.required])
    })
      
    this.documentoForm.setValue({id:documento.id, sigla:documento.sigla, nome:documento.nome, copias:documento.copias});
  }

  findByName(name){
    if(name ==="" ||typeof name == "undefined"){
      this.findAll(this.page,this.count);
    }else{
      this.isSearch = true;
      this.name = name;
      this.documentoService.findByNome(name,this.page,this.count).subscribe((responseApi:ResponseApi) => {
        this.totalElements = responseApi['data']['totalElements'];
        this.documentos = responseApi['data']['content'];
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
