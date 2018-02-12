package modelo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pessoa {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nome;
	private String sobrenome;
	
	@Column (nullable = false, unique = true)
	private Long matricula;
	
	@Column (nullable = false)
	private String senha;
	
	@Column (nullable = false, unique = true)
	private String login;
	
	@ManyToOne
	private Perfil perfil = new Perfil();
	
	public Pessoa(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
		
	public String criptografarSenha() {
		String senhaUsuario = this.getSenha();
		String senhaCriptografada = null;
		
		MessageDigest algoritmo;
		byte messageDigest[];
		StringBuilder hexString;
		
		try {
			
			algoritmo = MessageDigest.getInstance("MD5");
			messageDigest = algoritmo.digest(senhaUsuario.getBytes("UTF-8"));
			hexString = new StringBuilder();
			
			for(byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			
			senhaCriptografada = hexString.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Senha normal: "+senhaUsuario+" - Senha criptografada: "+senhaCriptografada);
		return senhaCriptografada;
		
	}
		

}


