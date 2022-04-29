package br.com.alura.challenger.backendjava.Exception;

public class ArquivoImportacaoVazioException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArquivoImportacaoVazioException(String message){
        super(message);
    }
}
