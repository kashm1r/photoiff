package controle;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;

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
	
	private List<String> subMenusSelecionados = new ArrayList<String>();
		
	public List<String> getSubMenusSelecionados() {
		return subMenusSelecionados;
	}

	public void setSubMenusSelecionados(List<String> subMenusSelecionados) {
		this.subMenusSelecionados = subMenusSelecionados;
	}

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
		
	public void gravarMenu() {
		try {
				List<MenuItem> itensSelecionados = new ArrayList<MenuItem>();
				for(String item : subMenusSelecionados) {
					itensSelecionados.add(menuItemService.obtemPorId(Long.parseLong(item)));
				}
				getMenu().AddMenuItem(itensSelecionados);
				if(getMenu().getId()!= null){
					menuService.merge(getMenu());
				} else {
					menuService.create(getMenu());
				}
				
				idMenuItem = 0L;
				FacesContext.getCurrentInstance().addMessage("Menu", new FacesMessage("Menu cadastrado com sucesso!"));
				setMenu(new Menu());
				atualizarMenu();
				RequestContext.getCurrentInstance().execute("PF('menuDl').hide()");
		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Aviso!", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Ocorreu um erro ao incluir o menu. Entre em contato com administrador!"));
			e.printStackTrace();
		}
	}
	
	
	public void editarMenu(Menu men) {
		setMenu(men);			
	}
	
	public void deletarMenu(Menu men){
		try {	
			menuService.remove(men);
			atualizarMenu();
			 addMessage("Aviso", "Menu deletado com sucesso!");
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Aviso!", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso","Erro ao excluir menu, o mesmo está vinculado em um perfil!"));
			e.printStackTrace();
		}	 
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void exibirTelaCadastroMenu(Menu m) {
		if(getMenu().getId() == null) {
			setMenu(new Menu());
			RequestContext.getCurrentInstance().execute("PF('menuDl').show()");
		} else {
			setMenu(new Menu());
			RequestContext.getCurrentInstance().execute("PF('menuDl').show()");
		}
		setMenu(new Menu());
	}
	
	public void fecharTelaEditarMenu(Menu m) {
		if(getMenu().getId() == null) {
			setMenu(new Menu());
			RequestContext.getCurrentInstance().execute("PF('menuDl').hide()");
		} else {
			RequestContext.getCurrentInstance().execute("PF('menuDl').hide()");
		}
		
	}

}
