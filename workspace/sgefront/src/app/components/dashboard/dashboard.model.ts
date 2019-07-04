import { EmpresaMetric } from "./empresa.dashboard.model";
import { CursoMetric } from "./curso.dashboard.model";

export interface ContratoMetric{
    totalContratos:number;
    totalAlunos:number;
    contratosAtivos:number;
    contratosFinalizados:number;
    mediaSalarial:number;
    efetivados:number;
    naoEfetivados:number;
    totalEmpresas:number;
    totalCursos:number;
    empresas:EmpresaMetric;
    cursos:CursoMetric[];
}