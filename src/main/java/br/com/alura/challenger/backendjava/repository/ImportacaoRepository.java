package br.com.alura.challenger.backendjava.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.challenger.backendjava.model.Importacao;
import br.com.alura.challenger.backendjava.model.Usuario;

@Repository
public interface ImportacaoRepository extends JpaRepository<Importacao, LocalDate>  {
    public boolean existsByDataTransacoesImportadas(LocalDate localDate);

    @Query("SELECT new Importacao(i.dataTransacoesImportadas, i.dataHoraImportacao, u.nome) FROM Importacao i JOIN i.usuarioImportacao u ORDER BY i.dataTransacoesImportadas Desc ")
    public List<Importacao> findAllByOrderByDataTransacoesImportadasDesc();

    @Query("SELECT i FROM Importacao i join fetch i.transacoes join fetch i.usuarioImportacao u WHERE i.dataTransacoesImportadas =:dataTransacoesImportadas")
    public Importacao findByDataTransacao(LocalDate dataTransacoesImportadas);

    public boolean existsByUsuarioImportacao(Usuario usuario);
}
