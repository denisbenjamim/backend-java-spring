package br.com.alura.challenger.backendjava.model.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data 
@EqualsAndHashCode(of = {"codigo"})  
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_permissao")
public class Permissao {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long codigo;
	
	private String nome;
}
