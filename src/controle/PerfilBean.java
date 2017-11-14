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
import modelo.Perfil;
import service.MenuItemService;
import service.MenuService;
import service.PerfilService;

@ViewScoped
@ManagedBean
public class PerfilBean {
	
	@EJB
	PerfilService perfilService;
	
	@EJB
	MenuService menuService;
	
	@EJB
	MenuItemService menuItemService;
	
	private Perfil perfil = new Perfil();
	
	private Long idMenu = 0L;
	
	private List<Perfil> listaPerfis = new ArrayList<Perfil>();
	
	private List<Menu> listaMenus = new ArrayList<Menu>();
	
	private List<MenuItem> listaMenuItens = new ArrayList<MenuItem>();
	

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

	public List<Menu> getListaMenus() {
		return listaMenus;
	}

	public void setListaMenus(List<Menu> listaMenus) {
		this.listaMenus = listaMenus;
	}

	public List<MenuItem> getListaMenuItens() {
		return listaMenuItens;
	}

	public void setListaMenuItens(List<MenuItem> listaMenuItens) {
		this.listaMenuItens = listaMenuItens;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getListaPerfis() {
		return listaPerfis;
	}

	public void setListaPerfis(List<Perfil> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}
	
	@PostConstruct
	public void init(){
		setListaMenus(menuService.listAll());
		atualizarPerfis();
	}
	
	public void atualizarPerfis(){
		getListaPerfis().clear();
		setListaPerfis(perfilService.obtemPerfilComMenus());
	}
	
	public void addMenus() {
		getPerfil().addMenu(menuService.obtemPorId(idMenu));
		idMenu = 0L;
	}
	
	public void gravarPerfil(){
		if(getPerfil().getId()!= null){
			perfilService.merge(getPerfil());
		} else {
			perfilService.create(getPerfil());
		}
		
		atualizarPerfis();
		FacesContext.getCurrentInstance().addMessage("Sucesso!", new FacesMessage("Perfil Cadastrado com Sucesso!"));
		setPerfil(new Perfil());
	}
	
	public void editarPerfil(Perfil per){
		setPerfil(per);
	}
	
	public void deletarPerfil(Perfil perf){
		perfilService.remove(perf);
		atualizarPerfis();
	}
}
