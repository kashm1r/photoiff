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

@ViewScoped
@ManagedBean
public class MenuItemBean {
	
	@EJB
	MenuItemService menuItemService;
	
	private MenuItem menuItem = new MenuItem();
	
	private List<MenuItem> listaMenuItens = new ArrayList<MenuItem>();
	
	private Menu menu = new Menu();
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

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
		try {
			if(getMenuItem().getId() != null) {
				menuItemService.merge(getMenuItem());
			} else {
				menuItemService.create(getMenuItem());
			}
			
			setMenuItem(new MenuItem());
			FacesContext.getCurrentInstance().addMessage("Sucesso!", new FacesMessage("Sub-Menu Cadastrado com Sucesso!"));
			atualizarMenuItem();
			RequestContext.getCurrentInstance().execute("PF('subMenuDl').hide()");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Aviso!", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Ocorreu um erro ao incluir o usuário. Entre em contato com administrador!"));
			e.printStackTrace();
		}	
	}
	
	public void editarMenuItem(MenuItem menu) {
		setMenuItem(menu);
	}
	
	public void deletarMenuItem(MenuItem menu){
		try {
			menuItemService.remove(menu);
			atualizarMenuItem();
			 addMessage("Aviso", "SubMenu deletado com sucesso!");
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Aviso!", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso","Erro ao excluir submenu, o mesmo está vinculado em um menu!"));
			e.printStackTrace();
		}
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void exibirTelaCadastroSubMenu(MenuItem mI) {
		if(getMenuItem().getId() == null) {
			setMenuItem(new MenuItem());
			RequestContext.getCurrentInstance().execute("PF('subMenuDl').show()");
		} else {
			setMenuItem(new MenuItem());
			RequestContext.getCurrentInstance().execute("PF('subMenuDl').show()");
		}
		setMenuItem(new MenuItem());
	}
	
	public void fecharTelaEditarSubMenu(MenuItem mI) {
		if (getMenuItem().getId() != null) {
			setMenuItem(new MenuItem());
			RequestContext.getCurrentInstance().execute("PF('subMenuDl').hide()");
		} else {
			RequestContext.getCurrentInstance().execute("PF('subMenuDl').hide()");
		}
		
	}
	
	
	

}
