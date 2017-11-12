package service;

import javax.ejb.Stateless;

import modelo.Perfil;

@Stateless
public class PerfilService extends GenericService<Perfil>{
	
	public PerfilService(){
		super(Perfil.class);
	}
	
}
