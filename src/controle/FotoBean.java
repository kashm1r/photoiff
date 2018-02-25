package controle;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
	
	@EJB
	FotoService fotoService;
	
	@EJB
	TagService tagService;
	
	@EJB
	PessoaService pessoaService;
	
	private Foto fotoModelo = new Foto();
	
	private List<Foto> listaFotos = new ArrayList<Foto>();
	
	private List<Tag> listaTags = new ArrayList<Tag>();
	
	private List<File> arquivos = new ArrayList<File>();

	private UploadedFile upload;
	
	private Date data = new Date();
	
	private Date dataDownload = new Date();
	
	private Pessoa usuario = new Pessoa();
		
	public Foto getFotoModelo() {
		return fotoModelo;
	}

	public void setFotoModelo(Foto fotoModelo) {
		this.fotoModelo = fotoModelo;
	}

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

//------------------------------------------------------VARIAVES TESTE-------------------------------------------------------
	//private List<StreamedContent> fotos;

	//private static final long serialVersionUID = 1L;

	private List<String> NomeFotos = new ArrayList<String>();
	
	private List<Foto> fotosCarregadas = new ArrayList<Foto>();
	
	public List<Foto> getFotosCarregadas() {
		return fotosCarregadas;
	}
	
	public void setFotosCarregadas(List<Foto> fotosCarregadas) {
		this.fotosCarregadas = fotosCarregadas;
	}
	
	public List<String> getNomeFotos() {
		return NomeFotos;
	}
	
	public void setNomeFotos(List<String> nomeFotos) {
		NomeFotos = nomeFotos;
	}
 //----------------------------------------------------------------------------------------------------------------------------
	 

 //-----------------------------------------------AREA PARA CRIACAO DAS FUNÇÕES-------------------------------------------------

	public Pessoa pegarUsuarioLogado() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		Pessoa user = (Pessoa) session.getAttribute("usuario");

		return user;	
	}

	public void exibirTelaImportacaoDeFotos() {
		listaFotos = new ArrayList<Foto>();
		RequestContext.getCurrentInstance().execute("PF('fotoDl').show()");
		
	}
	
	public void fecharTelaImportacaoDeFoto() {
		atualizarFotos(listaFotos);
		RequestContext.getCurrentInstance().execute("PF('fotoDl').hide()");
	}

	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static File diretorioRaiz() {
        File dir = new File(System.getProperty("user.home"), "testandoupload");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
	}
	
	public static File escrever(String name, byte[] contents) throws IOException {
		File file = new File(diretorioRaiz(), name);

		OutputStream out = new FileOutputStream(file);
		out.write(contents);
		out.close();

		return file;
	}

	public void importarFotos(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();

		try {
			String novoNomeFoto = fotoModelo.trocarNomeFotoImportada(uploadedFile.getFileName());
			File arquivo = FotoBean.escrever(novoNomeFoto, uploadedFile.getContents());
			arquivos.add(arquivo);
			
			
	//-----------------processo para gravar no banco as fotos--------------------------------
				Foto foto = new Foto();
				foto.setUrl(arquivo.getAbsolutePath());
				foto.setNome(novoNomeFoto); 
				foto.setPessoa(pegarUsuarioLogado());
				foto.setDataDownload(null);
				foto.setDataUpload(data);
				foto.setDiretorio(null);
				foto.setTags(null); //verificar se ja existe, se nao cvriar e vincular, caso contraro busca e vinculsr
				foto.setHabilitarVisualizacao(Boolean.FALSE);
				
				fotoService.create(foto);
				
				listaFotos.add(foto);
				
				foto = new Foto();		
	//--------------------------------------------------------------------------------------
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Upload completo", "O arquivo " + arquivo.getName() + " foi salvo!"));
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		}
	}

 //-----------------------------------------------------------------------------------------------------------------------------
 
 
	
 // ------------------------------------------------FUNCOES TESTE---------------------------------------------------------------
	
	//Chamar esse metodo ao carregar a imagem para exibir na grade no tamanho certo
	public ImageIcon redimensionarFotos(File image, int novaLargura, int novaAltura) {
		try {
			BufferedImage imagem = ImageIO.read(image);
			BufferedImage novaImagem = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = novaImagem.createGraphics();
			
			g.drawImage(imagem, 0, 0, novaAltura, novaLargura, null);
			g.dispose();
			
			return new ImageIcon(novaImagem);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void atualizarFotos(List<Foto> fts) {
		//fotos = new ArrayList<StreamedContent>();
		//fts = fotoService.listAll();
		for (Foto f : fts) {
			File fotoAtual = new File(diretorioRaiz(), f.getNome());
			//File fotoAtual = new File(f.getUrl());
			try {
				StreamedContent sfoto = new DefaultStreamedContent(new FileInputStream(fotoAtual), "image/jpg");
				//fotos.add(sfoto);
				f.setFotoMemoria(sfoto);
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			}
			
		}
	} 

	public final void inicializar(ComponentSystemEvent event) {
		//listaFotos = new ArrayList<Foto>();
		//atualizarFotos(fotosCarregadas);
		
		//atualizarFotos(listaFotos);
		//listaFotos = new ArrayList<Foto>();
	}
	
	@PostConstruct
	public void postConstruct() {
		//arquivos = new ArrayList<File>(FotoBean.listar());
		//atualizarFotos();
		listaFotos = new ArrayList<Foto>();
	}

	/*public static java.io.File diretorioRaizParaArquivos() {
		File dir = new File(diretorioRaiz(), "arquivos");
		
        if (!dir.exists()) {
           dir.mkdirs();
       }

      return dir;
    }
	
	public static List<File> listar() {
		File dir = diretorioRaizParaArquivos();
		return Arrays.asList(dir.listFiles());
	}*/
	
	//-------------------------------------------------------------------------------------------------------------------------------
}
