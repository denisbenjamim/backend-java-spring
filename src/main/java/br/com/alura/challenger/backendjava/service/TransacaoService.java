package br.com.alura.challenger.backendjava.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.alura.challenger.backendjava.model.Transacao;
import br.com.alura.challenger.backendjava.service.ValidarTransacao.AnalisarContasBancarias;
import br.com.alura.challenger.backendjava.service.ValidarTransacao.AnalisarValorTransacao;
import lombok.Getter;

@Service
public class TransacaoService {
    @Getter
    private List<Transacao> transacoesSuspeitetas = new ArrayList<>();
    
    @Getter
    private Map<String, Transacao> contasBancariasSupeitas = new HashMap<>();

    public void validarTransacao(List<Transacao> transacoes){
        new AnalisarValorTransacao(transacoesSuspeitetas).analisar(transacoes);
        new AnalisarContasBancarias(contasBancariasSupeitas).analisar(transacoes); 
    }
}
