package br.com.alura.challenger.backendjava.Exception;

public class CampoInvalidoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampoInvalidoException(String message){
        super(message);
    }
}
