package br.com.alura.challenger.backendjava.Exception;

public class RecursoIndisponivelEncontradoException extends RuntimeException {
    public RecursoIndisponivelEncontradoException(String message){
        super(message);
    }

    public RecursoIndisponivelEncontradoException(){
        this("Recurso indisponivel");
    }
}
