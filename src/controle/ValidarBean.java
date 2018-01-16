package controle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import modelo.Pessoa;
import service.PessoaService;


@ViewScoped
@ManagedBean
public class ValidarBean {
	
	@EJB()
	private PessoaService pessoaService;
	
	private String loginUser = "";
	private String senhaUser = "";	
	
	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getSenhaUser() {
		return senhaUser;
	}

	public void setSenhaUser(String senhaUser) {
		this.senhaUser = senhaUser;
	}
	
	public void logar(ActionEvent actionEvent) {
		validaUsuario("/home.xhtml");
    }
	
	
	private void validaUsuario(final String destino) {
		try {
    		Pessoa pessoa = pessoaService.validarUsuario(loginUser, senhaUser);
    		System.out.println("Usuario logado: " + pessoa.getNome());
    		
    		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
    		final HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("usuario", pessoa);
			//session.setAttribute("pessoa", pessoa);
			
			ec.redirect(ec.getRequestContextPath() + destino);
		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensagem:", "Usuário ou senha invalido!"));
		}
	}
	

}
