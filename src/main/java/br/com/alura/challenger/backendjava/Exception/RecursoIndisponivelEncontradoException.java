package br.com.alura.challenger.backendjava.Exception;

public class RecursoIndisponivelEncontradoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecursoIndisponivelEncontradoException(String message){
        super(message);
    }

    public RecursoIndisponivelEncontradoException(){
        this("Recurso indisponivel");
    }
}
