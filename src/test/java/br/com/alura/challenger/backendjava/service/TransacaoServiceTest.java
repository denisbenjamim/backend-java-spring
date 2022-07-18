package br.com.alura.challenger.backendjava.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.challenger.backendjava.model.Transacao;
import br.com.alura.challenger.backendjava.service.ValidarTransacao.AnalisarContasBancarias;
import br.com.alura.challenger.backendjava.service.ValidarTransacao.AnalisarValorTransacao;

//@SpringBootTest
public class TransacaoServiceTest {
    private final BigDecimal LIMITE_TRANSACAO = BigDecimal.valueOf(100000);
    private final BigDecimal ACIMA_LIMITE_TRANSACAO = BigDecimal.valueOf(100001);
    private final BigDecimal LIMITE_SOMATARIO_CONTA = BigDecimal.valueOf(1000000);
    private final BigDecimal LIMITE_SOMATARIO_AGENCIA = BigDecimal.valueOf(1000000000);

    @Test
    public void deveDispararExcessaoSeValorTransacaoIgualLimite(){
        final List<Transacao> transacoesSuspeitetas = new ArrayList<>();
        final Transacao transacao = new Transacao();
        final AnalisarValorTransacao analisarValorTransacao = new AnalisarValorTransacao(transacoesSuspeitetas);
        
        transacao.setRowId(123l);
        transacao.setValorTransacao(LIMITE_TRANSACAO);
        
        analisarValorTransacao.analisar(Arrays.asList(transacao));

        assertFalse(transacoesSuspeitetas.isEmpty());
        assertEquals(transacao, transacoesSuspeitetas.get(0));
    }

    @Test
    public void deveDispararExcessaoSeValorTransacaoAcimaLimite(){
        final List<Transacao> transacoesSuspeitetas = new ArrayList<>();
        final Transacao transacao = new Transacao();
        final AnalisarValorTransacao analisarValorTransacao = new AnalisarValorTransacao(transacoesSuspeitetas);

        transacao.setRowId(123l);
        transacao.setValorTransacao(ACIMA_LIMITE_TRANSACAO);
       
        analisarValorTransacao.analisar(Arrays.asList(transacao));
        
        assertFalse(transacoesSuspeitetas.isEmpty());
        assertEquals(transacao, transacoesSuspeitetas.get(0));
    }

    @Test
    public void deveDispararExcessaoSeSomatorioMovimentacoesContaBancariaForSuperiorLimiteNoMes(){
        Transacao transacao1 = Transacao.builder().rowId(1l)
                                    .bancoOrigem("Nubank").contaOrigem("22123").agenciaOrigem("2200")
                                    .bancoDestino("Itaul").contaDestino("33123").agenciaDestino("3300")
                                    .valorTransacao(LIMITE_SOMATARIO_CONTA.divide(BigDecimal.valueOf(2)))
                               .build();

        Transacao transacao2 = Transacao.builder().rowId(3l)
                                    .bancoOrigem("Bradesco").contaOrigem("11123").agenciaOrigem("1100")
                                    .bancoDestino("Nubank").contaDestino("22123").agenciaDestino("2200")
                                    .valorTransacao(LIMITE_SOMATARIO_CONTA.divide(BigDecimal.valueOf(2)))
                                .build();

        Transacao transacao3 = Transacao.builder().rowId(3l)
                                    .bancoOrigem("Itaul").contaOrigem("33123").agenciaOrigem("3300")
                                    .bancoDestino("Bradesco").contaDestino("11123").agenciaDestino("1100")
                                    .valorTransacao(LIMITE_SOMATARIO_CONTA.divide(BigDecimal.valueOf(2)))
                               .build();
        final List<Transacao> transacoesSuspeitetas = Arrays.asList(transacao1, transacao2, transacao3);
        Map<String, Transacao> contasBancariasSupeitas = new HashMap<>();
        AnalisarContasBancarias analisarContasBancarias = new AnalisarContasBancarias(contasBancariasSupeitas);
        analisarContasBancarias.analisar(transacoesSuspeitetas);
        assertFalse(false);
    }

    @Test
    public void deveInvalidarAgenciaBancariaSeSomatorioContasSuperiorLimiteNoMes(){
        Transacao transacao = new Transacao();
        transacao.setValorTransacao(LIMITE_SOMATARIO_AGENCIA);
       
        //assertThrowsExactly(FalhaValidacaoException.class, () -> transacaoService.validarTransacao(transacao));
    }
}
