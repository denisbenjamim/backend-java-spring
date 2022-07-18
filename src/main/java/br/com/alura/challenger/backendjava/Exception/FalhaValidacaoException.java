package br.com.alura.challenger.backendjava.Exception;

public class FalhaValidacaoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FalhaValidacaoException(String message){
        super(message);
    }
}
