package service;

import javax.ejb.Stateless;

import modelo.Administrador;

@Stateless
public class AdministradorService extends GenericService<Administrador>{
	
	public AdministradorService(){
		super(Administrador.class);
	}
	
	

}
