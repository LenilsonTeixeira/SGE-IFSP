
export class Contrato {
    constructor(id:number, numeroContro:string, horasSemanais:number, salario:number, 
        dataInicial:Date, dataFinal:Date, dataRenovacao: Date, dataTermino:Date, situacaoContrato: string, statusTermino: string, aluno: AlunoDto, empresa: EmpresaDto) {
            this.id = id;
            this.numeroContrato = numeroContro;
            this.horasSemanais = horasSemanais;
            this.salario = salario;
            this.dataInicial = dataInicial;
            this.dataFinal = dataFinal;
            this.dataRenovacao = dataRenovacao;
            this.dataTermino = dataTermino;
            this.statusTermino = statusTermino;
            this.situacaoContrato = situacaoContrato;
            this.empresa = empresa;
            this.aluno = aluno;
    }

    id: number;
    numeroContrato: string;
    horasSemanais: number;
    salario: number;
    dataInicial: Date;
    dataFinal: Date;
    dataRenovacao: Date;
    dataTermino: Date;
    situacaoContrato: string;
    statusTermino:string;
    empresa: EmpresaDto;
    curso: CursoDto;
    aluno: AlunoDto;
}

export class EmpresaDto {
    constructor(id:number, nome:string, cnpj:string){
        this.id = id;
        this.cnpj = cnpj;
        this.nome =nome;
    }
    nome: string;
    cnpj: string;
    id: number;
}

export class CursoDto{
    constructor(id:number, nome:string){
        this.id = id;
        this.nome = nome;
    }

    id:number;
    nome:string;
}

export class AlunoDto{
    constructor(id:number, nome:string, prontuario:string, email: string, telefone: string, curso:CursoDto){
        this.id = id;
        this.nome = nome;
        this.prontuario = prontuario;
        this.email = email;
        this.telefone = telefone;
        this.curso = curso;
    }

    id:number;
    nome: string;
    prontuario:string;
    email:string;
    telefone:string;
    curso: CursoDto;
}

