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
	
	private List<String> listaPerfisSelecionados = new ArrayList<String>();
	
	public List<String> getListaPerfisSelecionados() {
		return listaPerfisSelecionados;
	}

	public void setListaPerfisSelecionados(List<String> listaPerfisSelecionados) {
		this.listaPerfisSelecionados = listaPerfisSelecionados;
	}

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
	
	public void gravarPerfil(){
		try {
			
			List<Menu> perfisSelecionados = new ArrayList<Menu>();
			for(String perfis : listaPerfisSelecionados) {
				perfisSelecionados.add(menuService.obtemPorId(Long.parseLong(perfis)));
			}
			getPerfil().addMenu(perfisSelecionados);
			if(getPerfil().getId() != null) {
				perfilService.merge(getPerfil());
			} else {
				perfilService.create(getPerfil());
			}	
				FacesContext.getCurrentInstance().addMessage("Perfil", new FacesMessage("Perfil cadastrado com sucesso!"));
				setPerfil(new Perfil());
				atualizarPerfis();
				RequestContext.getCurrentInstance().execute("PF('perfilDl').hide()");
		} catch (Exception e) { 
			FacesContext.getCurrentInstance().addMessage("Aviso!", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Ocorreu um erro ao incluir o perfil. Entre em contato com administrador!"));
			e.printStackTrace();
		}
	}
	
	public void editarPerfil(Perfil per){
		setPerfil(per);
	}
	
	public void deletarPerfil(Perfil perf){
		perfilService.remove(perf);
		atualizarPerfis();
		addMessage("Aviso", "Perfil deletado com sucesso!");
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void exibirTelaCadastroPerfil(Perfil p) {
		if(getPerfil().getId() == null) {
			setPerfil(new Perfil());
			RequestContext.getCurrentInstance().execute("PF('perfilDl').show()");
		} else {
			setPerfil(new Perfil());
			RequestContext.getCurrentInstance().execute("PF('perfilDl').show()");
		}
		setPerfil(new Perfil());
	}
	
	public void fecharTelaEditarPerfil(Perfil p) {
		if(getPerfil().getId() == null) {
			setPerfil(new Perfil());
			RequestContext.getCurrentInstance().execute("PF('perfilDl').hide()");
		} else {
			RequestContext.getCurrentInstance().execute("PF('perfilDl').hide()");
		}
		
	}
}
