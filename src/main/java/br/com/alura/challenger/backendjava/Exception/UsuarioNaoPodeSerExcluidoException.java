package br.com.alura.challenger.backendjava.Exception;

public class UsuarioNaoPodeSerExcluidoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioNaoPodeSerExcluidoException(String message){
        super(message);
    }

    public UsuarioNaoPodeSerExcluidoException(){
        this("Usuário não pode ser excluido.");
    }
}
