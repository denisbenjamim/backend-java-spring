package br.com.alura.challenger.backendjava.service.ValidarTransacao;

import java.util.List;

import br.com.alura.challenger.backendjava.model.Transacao;

public interface AnalisadorAtividadeSuspeita {
    
    public void analisar(List<Transacao> transacoes);
}
