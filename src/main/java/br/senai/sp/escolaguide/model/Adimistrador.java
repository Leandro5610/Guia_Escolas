package br.senai.sp.escolaguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.escolaguide.util.HashUtil;
import lombok.Data;

// para gerar get e set
@Data
// para mapear como uma entidade JPA
@Entity
public class Adimistrador {
	//chave primária e auto-incremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	//metodo para setar a senha aplicando o hash
	public void setSenha(String senha) {
		//aplicao o hash e seta a senha no objeto
		this.senha = HashUtil.hash256(senha);
	}
	//metodo para setar a senha sem aplicar o hash
	public void setSenhaComHash(String hash) {
		//seta o hash na senha
		this.senha = hash;
	}
}
