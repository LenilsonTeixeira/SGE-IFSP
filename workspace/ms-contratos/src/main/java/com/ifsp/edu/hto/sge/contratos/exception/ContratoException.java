package com.ifsp.edu.hto.sge.contratos.exception;

public class ContratoException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public ContratoException(String mensagem){
        super(mensagem);
    }

    public ContratoException(String mensagem , Throwable cause){
        super(mensagem,cause);
    }
}
