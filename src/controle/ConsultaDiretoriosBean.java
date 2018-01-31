package controle;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import modelo.Diretorio;
import modelo.Pessoa;
import service.DiretorioService;



@SessionScoped
@ManagedBean
public class ConsultaDiretoriosBean {


	@EJB
	private DiretorioService diretoriosService;

	private TreeNode raiz;
	
	private TreeNode selectedNode;
	
	private String usuarioLogado;
	
	

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	@PostConstruct
	public void consultar() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		Pessoa user = (Pessoa) session.getAttribute("usuario");
		setUsuarioLogado(user.getNome());
		
		List<Diretorio> diretorioRaizes = diretoriosService.raizesComNos();

		raiz = new DefaultTreeNode("Raiz", null);

		adicionarNos(diretorioRaizes, raiz);
	}

	private void adicionarNos(List<Diretorio> diretorios, TreeNode pai) {
		for (Diretorio diretorio : diretorios) {
			
				TreeNode no = new DefaultTreeNode(diretorio, pai);
				diretorio.setSubdiretorios(diretoriosService.obtemNos(diretorio));
				adicionarNos(diretorio.getSubdiretorios(), no);

		}
	}

	public TreeNode getRaiz() {
		return raiz;
	}
	
	public void displaySelectedSingle() {
        if(selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }


}
