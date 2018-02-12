package controle;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import modelo.Pessoa;
import service.PessoaService;


@SessionScoped
@ManagedBean
public class ValidarBean {
	
	@EJB()
	private PessoaService pessoaService;
	
	private String loginUser = "";
	private String senhaUser = "";	
	private Pessoa usuario;
	private String senhaLogin;
			
	public String getSenhaLogin() {
		return senhaLogin;
	}

	public void setSenhaLogin(String senhaLogin) {
		this.senhaLogin = senhaLogin;
	}

	public Pessoa getUsuario() {
		return usuario;
	}

	public void setUsuario(Pessoa usuario) {
		this.usuario = usuario;
	}

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
		senhaLogin = compararSenhaCriptografada(senhaUser);
		senhaUser = senhaLogin;
		try {
    		Pessoa pessoa = pessoaService.validarUsuario(loginUser, senhaUser);
    		System.out.println("Usuario logado: " + pessoa.getNome());
    		
    		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
    		final HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("usuario", pessoa);
			
			ec.redirect(ec.getRequestContextPath() + destino);
		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensagem:", "Usuário ou senha invalido!"));
		}
	}
	
	public String compararSenhaCriptografada(String senha) {
		String senhaUsuario = senha;
		String senhaCriptografada = null;
		
		MessageDigest algoritmo;
		byte messageDigest[];
		StringBuilder hexString;
		
		try {
			
			algoritmo = MessageDigest.getInstance("MD5");
			messageDigest = algoritmo.digest(senhaUsuario.getBytes("UTF-8"));
			hexString = new StringBuilder();
			
			for(byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			
			senhaCriptografada = hexString.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Senha normal: "+senhaUsuario+" - Senha criptografada: "+senhaCriptografada);
		return senhaCriptografada;
		
	}	

}
