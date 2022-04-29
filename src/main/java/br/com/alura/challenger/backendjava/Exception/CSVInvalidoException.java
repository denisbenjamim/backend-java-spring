package br.com.alura.challenger.backendjava.Exception;

public class CSVInvalidoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CSVInvalidoException(String message){
        super(message);
    }
}
