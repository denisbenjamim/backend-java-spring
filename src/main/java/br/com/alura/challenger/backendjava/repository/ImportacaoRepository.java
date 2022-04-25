package br.com.alura.challenger.backendjava.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.challenger.backendjava.model.Importacao;

@Repository
public interface ImportacaoRepository extends JpaRepository<Importacao, LocalDate>  {
    public boolean existsByDataTransacoesImportadas(LocalDate localDate);

    @Query("SELECT new Importacao(i.dataTransacoesImportadas, i.dataHoraImportacao, i.usuarioImportacao.nome) FROM Importacao i ORDER BY i.dataTransacoesImportadas Desc ")
    public List<Importacao> findAllByOrderByDataTransacoesImportadasDesc();
}
