package controle;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import modelo.Menu;
import modelo.MenuItem;
import service.MenuItemService;
import service.MenuService;

@ViewScoped
@ManagedBean
public class MenuBean{
	
	@EJB
	MenuService menuService;
	
	@EJB
	MenuItemService menuItemService;
	
	private Menu menu = new Menu();
	
	private Long idMenuItem = 0L;
	
	private List<Menu> listaMenus = new ArrayList<Menu>();
	
	private List<MenuItem> listaMenuItens = new ArrayList<MenuItem>();
	
	public Long getIdMenuItem() {
		return idMenuItem;
	}

	public void setIdMenuItem(Long idMenuItem) {
		this.idMenuItem = idMenuItem;
	}

	public List<MenuItem> getListaMenuItens() {
		return listaMenuItens;
	}

	public void setListaMenuItens(List<MenuItem> listaMenuItens) {
		this.listaMenuItens = listaMenuItens;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getListaMenus() {
		return listaMenus;
	}

	public void setListaMenus(List<Menu> listaMenus) {
		this.listaMenus = listaMenus;
	}
	
	@PostConstruct
	public void init() {
		setListaMenuItens(menuItemService.listAll());
		atualizarMenu();
	}
	
	public void atualizarMenu() {
		getListaMenus().clear();
		setListaMenus(menuService.obtemMenuComMenuItens());
	}
	
	public void addMenuItens() {
		getMenu().AddMenuItem(menuItemService.obtemPorId(idMenuItem));
		idMenuItem=0L;
	}
	
	public void gravarMenu() {
		menuService.create(getMenu());
			
		atualizarMenu();
		setMenu(new Menu());
		FacesContext.getCurrentInstance().addMessage("Sucesso!", new FacesMessage("Menu Cadastrado com Sucesso!"));

	}
	
	
	public void editarMenu(Menu men) {
		setMenu(men);
		
		
	}
	

}
