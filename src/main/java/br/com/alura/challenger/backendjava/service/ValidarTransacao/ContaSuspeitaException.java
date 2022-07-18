package br.com.alura.challenger.backendjava.service.ValidarTransacao;

import br.com.alura.challenger.backendjava.Exception.FalhaValidacaoException;

public class ContaSuspeitaException extends FalhaValidacaoException {

    public ContaSuspeitaException(String message) {
        super(message);
    }

    public ContaSuspeitaException() {
        super("Transação Acima do Limite");
    }
}
