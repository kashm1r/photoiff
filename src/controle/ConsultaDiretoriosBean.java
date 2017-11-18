package controle;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import modelo.Diretorio;
import service.DiretorioService;



@ViewScoped
@ManagedBean
public class ConsultaDiretoriosBean {


	@EJB
	private DiretorioService diretoriosService;

	private TreeNode raiz;
	
	private TreeNode selectedNode;
	
	

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	@PostConstruct
	public void consultar() {
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
