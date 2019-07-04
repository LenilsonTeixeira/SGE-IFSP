package com.ifsp.edu.hto.sge.relatorios.exception;

public class RelatorioException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public RelatorioException(String mensagem){
        super(mensagem);
    }

    public RelatorioException(String mensagem , Throwable causa){
        super(mensagem,causa);
    }
}
