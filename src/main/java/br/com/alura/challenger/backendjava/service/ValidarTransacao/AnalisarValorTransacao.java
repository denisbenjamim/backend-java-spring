package br.com.alura.challenger.backendjava.service.ValidarTransacao;

import java.math.BigDecimal;
import java.util.List;

import br.com.alura.challenger.backendjava.model.Transacao;

public class AnalisarValorTransacao implements AnalisadorAtividadeSuspeita {
    private final BigDecimal LIMITE_TRANSACAO = BigDecimal.valueOf(100000);
    private final List<Transacao> transacoesSuspeitetas;

    public AnalisarValorTransacao(List<Transacao> transacoesSuspeitetas) {
        this.transacoesSuspeitetas = transacoesSuspeitetas;
    }

    @Override
    public void analisar(List<Transacao> transacoes) {
        transacoes.forEach(transacao -> {
            final int compare = LIMITE_TRANSACAO.compareTo(transacao.getValorTransacao()); 
            
            if(compare <= 0 ){
                transacoesSuspeitetas.add(transacao);
            }
        });
    }
}
