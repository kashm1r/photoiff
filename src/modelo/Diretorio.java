package modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Diretorio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	private Diretorio diretorioPai;
	private List<Diretorio> subDiretorio;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 80, nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@ManyToOne
	@JoinColumn(name = "diretorio_pai")
	public Diretorio getDiretorioPai() {
		return diretorioPai;
	}

	public void setDiretorioPai(Diretorio diretorioPai) {
		this.diretorioPai = diretorioPai;
	}

	@OneToMany(mappedBy = "diretorioPai", fetch = FetchType.LAZY)
	public List<Diretorio> getSubdiretorios() {
		return subDiretorio;
	}

	public void setSubdiretorios(List<Diretorio> subdiretorios) {
		this.subDiretorio = subdiretorios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diretorio other = (Diretorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	

}