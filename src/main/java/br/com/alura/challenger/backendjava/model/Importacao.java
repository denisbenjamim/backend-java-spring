package br.com.alura.challenger.backendjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_importacao")
public class Importacao {

    @Id
    @Column(name = "dt_transacoes_importadas")
    private LocalDate dataTransacoesImportadas;
    
    @Column(name = "dt_hr_importacao")
    private LocalDateTime dataHoraImportacao;
   
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "importacao", cascade = {CascadeType.ALL})
    private List<Transacao> transacoes;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cd_usuario_importacao", foreignKey = @ForeignKey(name = "FK_USUARIOIMPORTACAO_EM_IMPORTACAO"))
    private Usuario usuarioImportacao;

    @PrePersist
    private void prePersist(){
        this.dataHoraImportacao = LocalDateTime.now();
    }

    public Importacao(LocalDate dataTransacoesImportadas, LocalDateTime dataHoraImportacao, String nomeUsuario) {
        this.dataTransacoesImportadas = dataTransacoesImportadas;
        this.dataHoraImportacao = dataHoraImportacao;
        this.usuarioImportacao = Usuario.builder().nome(nomeUsuario).build();
    }
    
}
