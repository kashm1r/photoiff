package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.MenuItem;
import service.MenuItemService;

@ViewScoped
@ManagedBean
public class MenuItemBean {
	
	@EJB
	MenuItemService menuItemService;
	
	private MenuItem menuItem = new MenuItem();
	
	private List<MenuItem> listaMenuItens = new ArrayList<MenuItem>();

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public List<MenuItem> getListaMenuItens() {
		return listaMenuItens;
	}

	public void setListaMenuItens(List<MenuItem> listaMenuItens) {
		this.listaMenuItens = listaMenuItens;
	}
	
	@PostConstruct
	public void init() {
		atualizarMenuItem();
	}
	
	public void atualizarMenuItem() {
		getListaMenuItens().clear();
		setListaMenuItens(menuItemService.listAll());
	}
	
	public void gravarMenuItem() {
		if(getMenuItem().getId() != null) {
			menuItemService.merge(getMenuItem());
		} else {
			menuItemService.create(getMenuItem());
		}
		
		atualizarMenuItem();
		setMenuItem(new MenuItem());
		FacesContext.getCurrentInstance().addMessage("Sucesso!", new FacesMessage("Sub-Menu Cadastrado com Sucesso!"));
	}
	
	public void editarMenuItem(MenuItem menu) {
		setMenuItem(menu);
	}
	

}
