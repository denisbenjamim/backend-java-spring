package br.com.alura.challenger.backendjava.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.NonNull;

import br.com.alura.challenger.backendjava.model.security.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rowId;
    
    @NonNull
    private String nome;
    @NonNull
    private String email;
    @Transient
    private String senha;
    private String hashSenha;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "tb_usuario_grupo",
        joinColumns = @JoinColumn(name = "codigo_usuario", foreignKey = @ForeignKey(name = "FK_USUARIO_EM_USUARIOGRUPO")), 
        inverseJoinColumns = @JoinColumn(name = "codigo_grupo", foreignKey = @ForeignKey(name = "FK_GRUPO_EM_USUARIOGRUPO")))	
	private List<Grupo> grupos;

    public Usuario(Long rowId, String nome, String email, String hashSenha) {
        this.rowId = rowId;
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
    }

    
}
