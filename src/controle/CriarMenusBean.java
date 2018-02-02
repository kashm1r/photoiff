package controle;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import modelo.Menu;
import modelo.MenuItem;
import modelo.Pessoa;

@SessionScoped
@ManagedBean
public class CriarMenusBean {
	
	private MenuModel model;

	public MenuModel getModel() {
		MenuBean();
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	private Pessoa getPessoa() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		final HttpSession session = (HttpSession) ec.getSession(true);
		return (Pessoa) session.getAttribute("usuario");
	}
	
	public void MenuBean() {
		setModel(new DefaultMenuModel());
		final Pessoa pessoa = getPessoa();
		
		for(final Menu menu : pessoa.getPerfil().getMenus()) {
			
			final DefaultSubMenu submenu = new DefaultSubMenu("Dynamic Actions");
			
			submenu.setLabel(menu.getNome());
			submenu.setId("SM_" + menu.getId().toString());
			
			for(final MenuItem itemMenu : menu.getItens()) {
				
				final DefaultMenuItem item = new DefaultMenuItem("Dynamic Actions");
				
				item.setValue(itemMenu.getNome());
				item.setUrl(itemMenu.getUrl());
				item.setId("IM_" + itemMenu.getId().toString());
				
				submenu.addElement(item);
			}
			
			model.addElement(submenu);
		}
		
	}
	

}

