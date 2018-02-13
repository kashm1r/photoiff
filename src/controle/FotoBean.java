package controle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import modelo.Foto;
import modelo.Pessoa;
import modelo.Tag;
import service.FotoService;
import service.PessoaService;
import service.TagService;


@ViewScoped
@ManagedBean
public class FotoBean {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	FotoService fotoService;
	
	@EJB
	TagService tagService;
	
	@EJB
	PessoaService pessoaService;
	
	private Foto foto = new Foto();
	
	private List<Foto> listaFotos = new ArrayList<Foto>();
	
	private List<Tag> listaTags = new ArrayList<Tag>();
	
	private List<File> arquivos = new ArrayList<File>();
		
	private UploadedFile upload;
	
	private Date data = new Date();
	
	private Date dataDownload = new Date();
	
	private Pessoa usuario = new Pessoa();
		
	public Date getDataDownload() {
		return dataDownload;
	}

	public void setDataDownload(Date dataDownload) {
		this.dataDownload = dataDownload;
	}
	
	public List<File> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<File> arquivos) {
		this.arquivos = arquivos;
	}

	public Pessoa getUsuario() {
		return usuario;
	}

	public void setUsuario(Pessoa usuario) {
		this.usuario = usuario;
	}

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
	
	public UploadedFile getUpload() {
		return upload;
	}

	public void setUpload(UploadedFile upload) {
		this.upload = upload;
	}
	
	public List<Foto> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Foto> listaFotos) {
		this.listaFotos = listaFotos;
	}

	//----------------------------------------AREA PARA CRIACAO DAS FUNÇÕES---------------------------------------

	@PostConstruct
	public void postConstruct() {
		arquivos = new ArrayList<File>(FotoBean.listar());
	}

	public void importarFotos(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();

		try {
			listaFotos.clear();
			File arquivo = FotoBean.escrever(uploadedFile.getFileName(), uploadedFile.getContents());
			arquivos.add(arquivo);
			
			
	//-----------------processo para gravar no banco as fotos--------------------------------
			
				foto.setUrl(arquivo.getAbsolutePath());
				foto.setNome(uploadedFile.getFileName());
				foto.setPessoa(pegarUsuarioLogado());
				foto.setDataDownload(null);
				foto.setDataUpload(data);
				foto.setDiretorio(null);
				foto.setTags(null);
				
				fotoService.create(foto);
				
				foto = new Foto();
				
	//--------------------------------------------------------------------------------------
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Upload completo", "O arquivo " + arquivo.getName() + " foi salvo!"));
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		}
	}
		
	public static File escrever(String name, byte[] contents) throws IOException {
		File file = new File(diretorioRaiz(), name);

		OutputStream out = new FileOutputStream(file);
		out.write(contents);
		out.close();

		return file;
	}
	
	public static File diretorioRaiz() {
        // Estamos utilizando um diretório dentro da pasta temporária. 
        // No seu projeto, imagino que queira mudar isso para algo como:
        // File dir = new File(System.getProperty("user.home"), "algaworks");
        File dir = new File(System.getProperty("user.home"), "testandoupload");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }
	
	
	public static java.io.File diretorioRaizParaArquivos() {
        File dir = new File(diretorioRaiz(), "arquivos");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }
	
	public static List<File> listar() {
		File dir = diretorioRaizParaArquivos();

		return Arrays.asList(dir.listFiles());
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
	
	public Pessoa pegarUsuarioLogado() {
		
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		Pessoa user = (Pessoa) session.getAttribute("usuario");

		return user;	
	}
	

}
