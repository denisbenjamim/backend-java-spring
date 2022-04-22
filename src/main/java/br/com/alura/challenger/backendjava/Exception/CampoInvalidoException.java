package br.com.alura.challenger.backendjava.Exception;

public class CampoInvalidoException extends RuntimeException {
    public CampoInvalidoException(String message){
        super(message);
    }
}
