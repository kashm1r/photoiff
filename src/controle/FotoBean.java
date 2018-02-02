package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import modelo.Foto;
import modelo.Tag;
import service.FotoService;
import service.PessoaService;
import service.TagService;

@ViewScoped
@ManagedBean
public class FotoBean {
	
	@EJB
	FotoService fotoService;
	
	@EJB
	TagService tagService;
	
	@EJB
	PessoaService pessoaService;
	
	private Foto foto = new Foto();
	
	private List<Tag> listaTags = new ArrayList<Tag>();
	
	private Long idPessoa = 0L;
	
	private Long idTag = 0L;
	
	private UploadedFile upload;
	
	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public List<Tag> getListaTags() {
		return listaTags;
	}

	public void setListaTags(List<Tag> listaTags) {
		this.listaTags = listaTags;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getIdTag() {
		return idTag;
	}

	public void setIdTag(Long idTag) {
		this.idTag = idTag;
	}
	

	//----------------------------------------AREA PARA CRIACAO DAS FUNÇÕES---------------------------------------
	
	
	public UploadedFile getUpload() {
		return upload;
	}

	public void setUpload(UploadedFile upload) {
		this.upload = upload;
	}

	public void importarFotos(FileUploadEvent event) {
		upload = event.getFile();
		
		if(upload != null) {
			File arquivo1 = new File(/*"caminho que vou salvar a imagem"*/ upload.getFileName());
			try {
				
				FileOutputStream fos = new FileOutputStream(arquivo1);
				fos.write(event.getFile().getContents());
				fos.close();
				
				FacesContext instance = FacesContext.getCurrentInstance();
				instance.addMessage("mensagens", new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        upload.getFileName() + " anexado com sucesso", null));
				
			} catch (FileNotFoundException e) {
                e.printStackTrace();
				
			} catch (IOException e) {
                
                e.printStackTrace();
			}
		}	
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void exibirTelaImportacaoDeFotos(Foto f) {
		if(getFoto().getId() == null) {
			setFoto(new Foto());
			RequestContext.getCurrentInstance().execute("PF('fotoDl').show()");
		} else {
			setFoto(new Foto());
			RequestContext.getCurrentInstance().execute("PF('fotoDl').show()");
		}
		setFoto(new Foto());
	}
	
	public void fecharTelaImportacaoDeFoto(Foto f) {
		if (getFoto().getId() != null) {
			setFoto(new Foto());
			RequestContext.getCurrentInstance().execute("PF('fotoDl').hide()");
		} else {
			RequestContext.getCurrentInstance().execute("PF('fotoDl').hide()");
		}
		
	}

}
