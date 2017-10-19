package service;

import javax.ejb.Stateless;

import modelo.Usuario;

@Stateless
public class UsuarioService extends GenericService<Usuario>{
	
	public UsuarioService(){
		super(Usuario.class);		
	}

}
