package br.com.alura.challenger.backendjava.model.security;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor 
@AllArgsConstructor
@Data 
@EqualsAndHashCode(of = {"codigo"}) 
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name="tb_grupo")
public class Grupo {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long codigo;

	private String nome;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.EXTRA)
    @JoinTable(name = "tb_grupo_permissao",
            joinColumns = @JoinColumn(name = "codigo_grupo",foreignKey = @ForeignKey(name="FK_GRUPO_EM_GRUPOPERMISSAO")), 
            inverseJoinColumns = @JoinColumn(name = "codigo_permissao",foreignKey = @ForeignKey(name="FK_PERMISSAO_EM_GRUPOPERMISSAO")))
	private List<Permissao> permissoes;
}
