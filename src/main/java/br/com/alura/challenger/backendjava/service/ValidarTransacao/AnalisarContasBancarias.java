package br.com.alura.challenger.backendjava.service.ValidarTransacao;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.alura.challenger.backendjava.Exception.FalhaValidacaoException;
import br.com.alura.challenger.backendjava.model.Transacao;

public class AnalisarContasBancarias implements AnalisadorAtividadeSuspeita{
    private final static BigDecimal LIMITE_SOMATARIO_CONTA = BigDecimal.valueOf(1000000);
    private final Map<String, Transacao> contasBancariasSupeitas;
    private Map<String, BigDecimal> contasBancariasSupeitasCache = new HashMap<>();


    public AnalisarContasBancarias(Map<String, Transacao> contasBancariasSupeitas) {
        this.contasBancariasSupeitas = contasBancariasSupeitas;
    }
    
    @Override
    public void analisar(List<Transacao> transacoes) throws FalhaValidacaoException {
        transacoes.forEach(transacao -> {
            String keyContaOrigem = getKey(transacao.getBancoOrigem(), transacao.getContaOrigem());
            String keyContaDestino = getKey(transacao.getBancoDestino(), transacao.getContaDestino());

            BigDecimal valorTransacao = transacao.getValorTransacao();
            BigDecimal totalContaOrigem = contasBancariasSupeitasCache.get(keyContaOrigem);
            BigDecimal totalContaDestino = contasBancariasSupeitasCache.get(keyContaDestino);

            registrarValorTransacaoParaConta(keyContaOrigem, valorTransacao, totalContaOrigem);
            registrarValorTransacaoParaConta(keyContaDestino, valorTransacao, totalContaDestino);
        });

        for(Entry<String, BigDecimal> entry: contasBancariasSupeitasCache.entrySet()){
            if(entry.getValue().compareTo(LIMITE_SOMATARIO_CONTA) > 0){
              System.out.println(entry.getValue());  
            }
        }
    }

    private void registrarValorTransacaoParaConta(String keyConta,BigDecimal valorTransacao, BigDecimal totalAcumuladoConta){
        if(totalAcumuladoConta == null){
            totalAcumuladoConta = valorTransacao;
        } else {
            totalAcumuladoConta = totalAcumuladoConta.add(valorTransacao);
        }

        contasBancariasSupeitasCache.put(keyConta, totalAcumuladoConta);
    }

    private String getKey(String banco, String conta){
        return MessageFormat.format("{0}-{1}", banco, conta);
    }
}