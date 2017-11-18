package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Perfil {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nome;
	
	@ManyToMany
	private List<Menu> menus = new ArrayList<Menu>();
	
	public Perfil(){};	
	

	public List<Menu> getMenus() {
		return menus;
	}


	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void addMenu (Menu m) {
		getMenus().add(m);
	}


	/*@Override
	public String toString() {
		return "Perfil [nome=" + nome + ", menus=" + menus + "]";
	}*/
	
	@Override
	public String toString() {
		return nome;
	}
	

	
}
