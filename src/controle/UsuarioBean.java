package controle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Usuario;
import service.UsuarioService;

@ViewScoped
@ManagedBean
public class UsuarioBean {
	
	@EJB
	UsuarioService usuarioService;
	
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
