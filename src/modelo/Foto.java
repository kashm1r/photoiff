package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Foto {
	
	@Id @GeneratedValue
	private Long id;
	
	private String url;
	
	@Column (nullable = false, unique = true)
	private String nome;
	
	private Date dataUpload;
	
	private Date dataDownload;
	
	@ManyToOne
	private Pessoa pessoa = new Pessoa();
	
	@ManyToMany
	private List<Tag> tags = new ArrayList<Tag>();
	
	@ManyToOne
	private Diretorio diretorio = new Diretorio();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

	public Date getDataDownload() {
		return dataDownload;
	}

	public void setDataDownload(Date dataDownload) {
		this.dataDownload = dataDownload;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Diretorio getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}
	
	
	
	

}
