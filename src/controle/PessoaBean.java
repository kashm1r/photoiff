package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Perfil;
import modelo.Pessoa;
import service.PerfilService;
import service.PessoaService;

@ViewScoped
@ManagedBean
public class PessoaBean {
	
	@EJB
	PessoaService pessoaService;
	
	@EJB
	PerfilService perfilService;
	
	private Pessoa pessoa = new Pessoa();
	
	private Long idPerfil = 0L;
	
	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	
	private List<Perfil> listaPerfis = new ArrayList<Perfil>();
	

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public List<Perfil> getListaPerfis() {
		return listaPerfis;
	}

	public void setListaPerfis(List<Perfil> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}
	
	@PostConstruct
	public void init(){
		setListaPerfis(perfilService.listAll());
		atualizarPessoa();
	}
	
	public void atualizarPessoa(){
		getListaPessoas().clear();
		setListaPessoas(pessoaService.listAll());
	}
	
	public void gravarPessoa(){
		if(idPerfil == 0) {
			FacesContext.getCurrentInstance().addMessage("Pessoa", new FacesMessage("Perfil Inválido"));
		}else {
			getPessoa().setPerfil(perfilService.obtemPorId(idPerfil));
		}
		if(getPessoa().getId()!= null){
			pessoaService.merge(getPessoa());
		} else {
			pessoaService.create(getPessoa());
		}
		
		atualizarPessoa();
		idPerfil = 0L;
		FacesContext.getCurrentInstance().addMessage("Pessoa", new FacesMessage("Usuario cadastrado com sucesso!"));
		setPessoa(new Pessoa());
	}
	
	public void editarPessoa(Pessoa pes){
		setPessoa(pes);
		idPerfil=pes.getPerfil().getId();
	}
	
	public void deletarPessoa(Pessoa pess){
		pessoaService.remove(pess);
		atualizarPessoa();
	}
	
	


}
