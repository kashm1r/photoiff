package modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.primefaces.model.StreamedContent;

@Entity
public class Foto {
	
	@Id @GeneratedValue
	private Long id;
	
	private String url;
	
	private String nome;
	
	private Date dataUpload;
	
	private Date dataDownload;
	
	@ManyToOne
	private Pessoa pessoa = new Pessoa();
	
	@ManyToMany
	private List<Tag> tags = new ArrayList<Tag>();
	
	@ManyToOne
	private Diretorio diretorio = new Diretorio();
	
	@Column(columnDefinition="TINYINT(1)")
	private Boolean habilitarVisualizacao;
	
	@Transient
	private StreamedContent fotoMemoria;

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

	public Boolean getHabilitarVisualizacao() {
		return habilitarVisualizacao;
	}

	public void setHabilitarVisualizacao(Boolean habilitarVisualizacao) {
		this.habilitarVisualizacao = habilitarVisualizacao;
	}

	public StreamedContent getFotoMemoria() {
		return fotoMemoria;
	}

	public void setFotoMemoria(StreamedContent fotoMemoria) {
		this.fotoMemoria = fotoMemoria;
	}

	@Override
	public String toString() {
		return "Foto [url=" + url + ", nome=" + nome + "]";
	}
	
	public String trocarNomeFotoImportada(String nomeFotoAtual){

		Calendar calendario = Calendar.getInstance();
		String nomeTrocado = "";
		nomeTrocado = nomeFotoAtual;

		nomeTrocado = "inPICS.FT_";
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.MONTH) + 1);
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.YEAR));
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.MINUTE));
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.SECOND));
		nomeTrocado = nomeTrocado + String.valueOf(calendario.get(Calendar.MILLISECOND));

		return nomeTrocado;

	}


}
