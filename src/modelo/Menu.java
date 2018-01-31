package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Menu {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nome;
	
	@ManyToMany
	private List<MenuItem> itens = new ArrayList<MenuItem>();
	
	public Menu(){}
	

	public List<MenuItem> getItens() {
		return itens;
	}


	public void setItens(List<MenuItem> itens) {
		this.itens = itens;
	}


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
	
	public void AddMenuItem (List<MenuItem> itens) {
		getItens().clear();
		getItens().addAll(itens);
	}
	
	@Override
	public String toString() {
		return nome;
	}
	


}
