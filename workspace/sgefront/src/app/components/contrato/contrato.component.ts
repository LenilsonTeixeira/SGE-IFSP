import { Component, OnInit } from '@angular/core';
import { ContratoService } from './contrato.service';
import { EmpresaService } from '../empresa/empresa.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../shared/dialog.service';
import { Empresa } from '../empresa/empresa.model';
import { ResponseApi } from '../shared/response.api';
import { AlunoService } from '../aluno/aluno.service';
import { Aluno } from '../aluno/aluno.model';
import { Contrato, EmpresaDto, AlunoDto, CursoDto } from './contrato.model';

@Component({
  selector: 'sge-contrato',
  templateUrl: './contrato.component.html',
  styleUrls: ['./contrato.component.css']
})
export class ContratoComponent implements OnInit {

  constructor(private contratoService:ContratoService,
              private empresaService: EmpresaService,
              private alunoService: AlunoService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private dialogService: DialogService) { }
          
  contratoForm: FormGroup;
  contrato: Contrato;
  empresaDto:EmpresaDto;
  alunoDto:AlunoDto;
  cursoDto:CursoDto;
  contratos: Contrato[];
  empresas: Empresa[];
  page:number=0;
  count:number=10;
  pages:Array<number>;
  message : {};
  classCss : {};
  totalElements: number;
  isSearch: boolean = false;
  prontuario: string;
  codigo: number;
  idAluno:number;
  idEmpresa:number;
  aluno: Aluno;
  empresa: Empresa;


  ngOnInit() {
    this.findAll(this.page,this.count);
    this.contratoForm = this.formBuilder.group({
      idAluno:this.formBuilder.control(''),
      nome:this.formBuilder.control({value: '', disabled: true}),
      email:this.formBuilder.control({value: '', disabled: true}),
      telefone:this.formBuilder.control({value: '', disabled: true}),
      prontuario:this.formBuilder.control('',[Validators.required]),
      idCurso:this.formBuilder.control(''),
      curso:this.formBuilder.control({value: '', disabled: true}),
      codigo:this.formBuilder.control('',[Validators.required]),
      cnpj:this.formBuilder.control({value: '', disabled: true}),
      idEmpresa:this.formBuilder.control(''),
      empresa:this.formBuilder.control('',[Validators.required]),
      horasSemanais:this.formBuilder.control(''),
      salario:this.formBuilder.control(''),
      dataInicial:this.formBuilder.control(''),
      dataFinal:this.formBuilder.control(''),
      dataRenovacao:this.formBuilder.control(''),
      dataTermino:this.formBuilder.control(''),
      situacao:this.formBuilder.control('')
      //statusTermino:this.formBuilder.control('')

      })
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

  fillStudent(prontuario){
      this.prontuario = prontuario;
      this.contratoService.fillStudent(prontuario).subscribe((responseApi:ResponseApi) =>{
        this.aluno = responseApi['data'];
        this.contratoForm.setValue({idAluno:this.aluno.id, nome:this.aluno.nome, email:this.aluno.email, telefone:this.aluno.telefone, prontuario:this.aluno.prontuario, idCurso:this.aluno.curso.id, curso:this.aluno.curso.nome,
        codigo:null, cnpj:null,idEmpresa:null, empresa:null, horasSemanais:null,salario:null,dataInicial:null, dataFinal:null, dataRenovacao:null,dataTermino:null,situacao:null});
      })
  }

  fillCompany(codigo){
    this.codigo = codigo;
    this.contratoService.fillCompany(codigo).subscribe((responseApi:ResponseApi) =>{
      this.empresa = responseApi['data'];
      this.contratoForm.setValue({idAluno:this.aluno.id, nome:this.aluno.nome, email:this.aluno.email, telefone:this.aluno.telefone, prontuario:this.aluno.prontuario, idCurso:this.aluno.curso.id, curso:this.aluno.curso.nome,
      idEmpresa: this.empresa.id, codigo:this.empresa.codigo, cnpj:this.empresa.cnpj, empresa:this.empresa.nome, horasSemanais:null, salario:null, dataInicial:null, dataFinal:null, dataRenovacao:null, dataTermino:null, situacao:null});
    })
}

alunoFindById(id){
  this.contratoService.alunoFindById(id).subscribe((responseApi:ResponseApi) => {
    this.aluno = responseApi['data'];
  })
}

empresaFindById(id){
  this.contratoService.empresaFindById(id).subscribe((responseApi:ResponseApi) => {
    this.empresa = responseApi['data'];
  })
}

create(contrato){
  this.contratoService.create(contrato).subscribe((responseApi:ResponseApi)=>{
    this.contrato = responseApi.data; 
  });
  }

addContrato(contrato){
  this.contratos.push(contrato); 
}

  save(){
      console.log(this.contratoForm.getRawValue());
      var alunoId = this.contratoForm.getRawValue().idAluno;
      var alunoNome = this.contratoForm.getRawValue().nome;
      var alunoProntuario = this.contratoForm.getRawValue().prontuario;
      var alunoEmail = this.contratoForm.getRawValue().email;
      var alunoTelefone = this.contratoForm.getRawValue().telefone;

      var empresaId = this.contratoForm.getRawValue().idEmpresa;
      var empresaNome = this.contratoForm.getRawValue().empresa;
      var empresaCnpj = this.contratoForm.getRawValue().cnpj;

      var cursoId = this.contratoForm.getRawValue().idCurso;
      var cursoNome = this.contratoForm.getRawValue().curso;

      var contratoId = this.contratoForm.getRawValue().idContrato;
      var contratoNumero = null; //this.contratoForm.getRawValue()
      var contratoSalario = this.contratoForm.getRawValue().salario;
      var contratoHorasSemanais = this.contratoForm.getRawValue().horasSemanais;
      var contratoDataInicial = new Date(this.contratoForm.getRawValue().dataInicial);
      var contratoDataFinal = new Date(this.contratoForm.getRawValue().dataFinal);
      var contratoDataRenovacao = new Date(this.contratoForm.getRawValue().dataRenovacao);
      var contratoDataTermino = new Date(this.contratoForm.getRawValue().dataTermino);
      var contratoSituacao = this.contratoForm.getRawValue().situacao;
      var contratoStatusTermino = null; //this.contratoForm.getRawValue().statusTermino;

      var cursoDto = new CursoDto(cursoId, cursoNome);
      var empresaDto = new EmpresaDto(empresaId, empresaNome, empresaCnpj);
      var alunoDto = new AlunoDto(alunoId, alunoNome, alunoProntuario, alunoEmail, alunoTelefone, cursoDto);
      var contrato = new Contrato(contratoId, contratoNumero, contratoHorasSemanais, contratoSalario, contratoDataInicial, contratoDataFinal, contratoDataRenovacao, contratoDataTermino, contratoSituacao, contratoStatusTermino, alunoDto, empresaDto);

      this.create(contrato);
      this.showMessage({
        type: 'success',
        text: `Contrato salvo com sucesso!`
      });
      this.contratoForm.reset();
  
      setTimeout(()=>{ this.findAll(this.page,this.count) }, 2000);
  }

  delete(id:number){
    this.dialogService.confirm('Tem certeza que deseja deletar esse contrato ?')
      .then((candelete:boolean) => {
          if(candelete){
            this.message = {};
            this.contratoService.delete(id).subscribe((responseApi:ResponseApi) => {
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

  findAll(page:number,count:number){
    this.contratoService.findAll(page,count).subscribe((responseApi:ResponseApi) => {
        console.log(responseApi);
        this.totalElements = responseApi['data']['totalElements'];
        this.contratos = responseApi['data']['content'];
        this.pages = new Array(responseApi['data']['totalPages']);
    } , err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });

  }



}
