package br.com.alura.challenger.backendjava.Exception;

public class EmailJaExisteException extends RuntimeException {
    public EmailJaExisteException(String message){
        super(message);
    }
}
