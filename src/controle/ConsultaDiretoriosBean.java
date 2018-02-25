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
	
	private Diretorio diretorio = new Diretorio();
	
	private List<Diretorio> listaDiretorio = new ArrayList<Diretorio>();
		
	public List<Diretorio> getListaDiretorio() {
		return listaDiretorio;
	}

	public void setListaDiretorio(List<Diretorio> listaDiretorio) {
		this.listaDiretorio = listaDiretorio;
	}

	public Diretorio getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}
	
	public TreeNode getRaiz() {
		return raiz;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}

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

	public void displaySelectedSingle() {
        if(selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	public void removerParticao() {
		if(selectedNode != null) {
			if(selectedNode.getChildCount() > 0) {
				 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Partição Principal", "Favor remover as partições secundárias");
		         FacesContext.getCurrentInstance().addMessage(null, message);
			}
			
			else {
				System.out.println(selectedNode.getData().toString());
				Diretorio dir = diretoriosService.buscarDiretorio(selectedNode.getData().toString());
				diretoriosService.remove(dir);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Partição Excluída", selectedNode.getData().toString());
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
	}
	
	public void adicionarParticao() {
		if(selectedNode == null) {
			// Criar uma partição pai, setando o diretorio pai como nulo			
		} else {
			// Criar uma subpartição através de um objeto já existente, setando o diretório pai igual ao ID do mesmo
		}
	}
	
	public void editarParticao(Diretorio dir) {
		setDiretorio(dir);
		
	}
	
	public void exibirTelaCadastroDiretorio(Diretorio d) {
		if(getDiretorio().getId() == null) {
			setDiretorio(new Diretorio());
			RequestContext.getCurrentInstance().execute("PF('particaoDl').show()");
		} else {
			setDiretorio(new Diretorio());
			RequestContext.getCurrentInstance().execute("PF('particaoDl').show()");
		}
		setDiretorio(new Diretorio());
	}
	
	public void fecharTelaEditarDiretorio(Diretorio d) {
		if(getDiretorio().getId() == null) {
			setDiretorio(new Diretorio());
			RequestContext.getCurrentInstance().execute("PF('particaoDl').hide()");
		} else {
			RequestContext.getCurrentInstance().execute("PF('particaoDl').hide()");
		}
		
	}


}
