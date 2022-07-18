package br.com.alura.challenger.backendjava.service.ValidarTransacao;

import br.com.alura.challenger.backendjava.Exception.FalhaValidacaoException;

public class TransacaoSuspeitaException extends FalhaValidacaoException {

    public TransacaoSuspeitaException(String message) {
        super(message);
    }

    public TransacaoSuspeitaException() {
        super("Transação Acima do Limite");
    }
}
