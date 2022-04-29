package br.com.alura.challenger.backendjava.Exception;

public class UsuarioNaoPodeSerAlteradoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioNaoPodeSerAlteradoException(String message){
        super(message);
    }

    public UsuarioNaoPodeSerAlteradoException(){
        this("Usuário não pode ser alterado.");
    }
}
