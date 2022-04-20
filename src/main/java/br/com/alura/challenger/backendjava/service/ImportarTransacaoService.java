package br.com.alura.challenger.backendjava.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alura.challenger.backendjava.model.CSVInvalidoException;
import br.com.alura.challenger.backendjava.model.Importacao;
import br.com.alura.challenger.backendjava.model.Transacao;
import br.com.alura.challenger.backendjava.repository.ImportacaoRepository;
import br.com.alura.challenger.backendjava.utils.LerArquivoImportarTransacao;
import br.com.alura.challenger.backendjava.utils.ManipularArquivo;

@Service
public class ImportarTransacaoService {

    @Autowired
    private ImportacaoRepository ImportacaoRepository;

    public void processarArquivo(MultipartFile file) throws ArquivoImportacaoVazioException, DataImportacaoJaRealizadaException, CSVInvalidoException {
        if (file.isEmpty()) {
            throw new ArquivoImportacaoVazioException("O arquivo não pode estar vazio");
        }
        
        exibirNomeETamanhoArquivoCarregado(file);

        try {
            final ManipularArquivo<Transacao> ARQUIVO = new LerArquivoImportarTransacao(file.getBytes(), ",");
            final List<Transacao> TRANSACOES = ARQUIVO.get();
            final LocalDate DATA_BASE_TRANSACOES = TRANSACOES.get(0).getDataTransacao();

            if (ImportacaoRepository.existsByDataTransacoesImportadas(DATA_BASE_TRANSACOES)) {
                Date data = Date.from(DATA_BASE_TRANSACOES.atStartOfDay(ZoneId.systemDefault()).toInstant());

                throw new DataImportacaoJaRealizadaException(
                        MessageFormat.format("Já foi realizada importação para data {0,date,short}", data));
            }

            final Importacao IMPORTACAO = TRANSACOES.get(0).getImportacao();

            final List<Transacao> TRANSACOES_FILTRADAS = TRANSACOES.stream().filter(tr -> tr.getDataTransacao().equals(DATA_BASE_TRANSACOES)).collect(Collectors.toList());
            
            IMPORTACAO.setTransacoes(TRANSACOES_FILTRADAS);
            IMPORTACAO.setDataTransacoesImportadas(DATA_BASE_TRANSACOES);

            ImportacaoRepository.save(IMPORTACAO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Importacao> todasImportacoesOrdendasPorDataTransacaoDesc() {
        return ImportacaoRepository.findAllByOrderByDataTransacoesImportadasDesc();
    }

    private void exibirNomeETamanhoArquivoCarregado(MultipartFile file) {
        String nomeArquivo = file.getOriginalFilename();
        double tamanhoArquivo = convertTamanhoParaMB(file.getSize());
        System.out.println(MessageFormat.format("Nome: {0} - Tamanho: {1} Mb", nomeArquivo, tamanhoArquivo));
    }

    private double convertTamanhoParaMB(long tamanho) {
        return Long.valueOf(tamanho).doubleValue() / 1024000;
    }
}
