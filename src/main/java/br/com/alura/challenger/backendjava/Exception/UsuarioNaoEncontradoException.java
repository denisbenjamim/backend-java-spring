package br.com.alura.challenger.backendjava.Exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message){
        super(message);
    }

    public UsuarioNaoEncontradoException(){
        this("Usuário não foi encontrado");
    }
}
