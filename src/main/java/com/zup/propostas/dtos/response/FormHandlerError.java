package com.zup.propostas.dtos.response;

public class FormHandlerError {

    private int status;

    private String campo;

    private String exception;

    private String mensagem;

    @Deprecated
    public FormHandlerError(){}

    public FormHandlerError(int status, String campo, String exception, String mensagem) {
        this.status = status;
        this.campo = campo;
        this.exception = exception;
        this.mensagem = mensagem;
    }

    public int getStatus() {
        return status;
    }

    public String getCampo() {
        return campo;
    }

    public String getException() {
        return exception;
    }

    public String getMensagem() {
        return mensagem;
    }
}
