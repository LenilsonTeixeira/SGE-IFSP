import { Curso } from "../curso/curso.model";

export interface Aluno{
    id:number;
    prontuario:string;
    nome:string;
    email:string;
    telefone:string;
    curso:Curso;
}