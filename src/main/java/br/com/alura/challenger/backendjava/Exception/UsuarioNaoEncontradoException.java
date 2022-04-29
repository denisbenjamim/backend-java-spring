package br.com.alura.challenger.backendjava.Exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String message){
        super(message);
    }

    public UsuarioNaoEncontradoException(){
        this("Usuário não foi encontrado");
    }
}
