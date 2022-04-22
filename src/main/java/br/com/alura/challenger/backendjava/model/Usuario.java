package br.com.alura.challenger.backendjava.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

import br.com.alura.challenger.backendjava.utils.Crypto;
import br.com.alura.challenger.backendjava.utils.GeradorSenha;
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

    @PrePersist
    private void prePersist(){
        this.senha = GeradorSenha.getSenhaAleatoria();        
        this.hashSenha = new Crypto("alurachallenger3").getHashSenha(senha);
    }

}
