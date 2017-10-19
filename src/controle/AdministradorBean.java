package controle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Administrador;
import service.AdministradorService;

@ViewScoped
@ManagedBean
public class AdministradorBean {
	
	@EJB
	AdministradorService administradorService;
	
	private Administrador administrador = new Administrador();

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
	

}
