package tools;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import modelo.Menu;
import modelo.MenuItem;
import modelo.Perfil;
import modelo.Pessoa;
import service.DiretorioService;
import service.PessoaService;

@ManagedBean
public class IniciarSistemaBean {

	@EJB
	PessoaService pessoaService;
	
	@EJB
	DiretorioService diretorioService;
	
	public void iniciar() {
		
		List<Pessoa> usuarios = pessoaService.listAll();
		if(usuarios.isEmpty()) {
			
//--------------------------------CRIAR SUBMENUS--------------------------
			
			MenuItem cadastroSubMenu = new MenuItem();
				cadastroSubMenu.setNome("Cadastro de SubMenus");
				cadastroSubMenu.setUrl("submenu.xhtml");
			
			
			MenuItem cadastroMenu = new MenuItem();
				cadastroMenu.setNome("Cadastro de Menus");
				cadastroMenu.setUrl("menu.xhtml");
			
			
			MenuItem cadastroPerfil = new MenuItem();
				cadastroPerfil.setNome("Cadastro de Perfil");
				cadastroPerfil.setUrl("perfil.xhtml");
			
			
			MenuItem cadastroUsuario = new MenuItem();
				cadastroUsuario.setNome("Cadastro de Usuarios");
				cadastroUsuario.setUrl("usuario.xhtml");
			
			
			MenuItem cadastroFoto = new MenuItem();
				cadastroFoto.setNome("Upload de Fotos");
				cadastroFoto.setUrl("foto.xhtml");
				
			MenuItem cadastroParticoes = new MenuItem();
				cadastroParticoes.setNome("Cadastro de Particoes");
				cadastroParticoes.setUrl("particao.xhtml");
				
//----------------------------------CRIAR MENUS------------------------------
				
			Menu cadastro = new Menu();
				cadastro.setNome("Cadastros");
				cadastro.getItens().add(cadastroUsuario);
				cadastro.getItens().add(cadastroParticoes);
				
			Menu administrativo = new Menu();
				administrativo.setNome("Administrativo");
				administrativo.getItens().add(cadastroPerfil);
				administrativo.getItens().add(cadastroSubMenu);
				administrativo.getItens().add(cadastroMenu);
				
			Menu fotos = new Menu();
				fotos.setNome("Fotos");
				fotos.getItens().add(cadastroFoto);
				
//------------------------------------CRIAR PERFIL----------------------------
				
			Perfil diretoria = new Perfil();
				diretoria.setNome("diretoria");
				diretoria.getMenus().add(cadastro);
				diretoria.getMenus().add(administrativo);
				diretoria.getMenus().add(fotos);
				
//-----------------------------------CRIAR USUARIO---------------------------
				
			Pessoa usuario = new Pessoa();
				usuario.setNome("administrador");
				usuario.setSobrenome("administrador");
				usuario.setMatricula(1L);
				usuario.setLogin("admin");
				usuario.setSenha("123");
				usuario.setSenha(usuario.criptografarSenha());
				usuario.setPerfil(diretoria);
				
				pessoaService.criarUsuarioComPerfilMenuSubMenu(usuario);
				
				FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage("Sistema iniciado com sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage("Aviso", new FacesMessage("Já existe dados gravados no banco"));
		}
		
	}

}
