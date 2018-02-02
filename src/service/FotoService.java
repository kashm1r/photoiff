package service;

import javax.ejb.Stateless;

import modelo.Foto;

@Stateless
public class FotoService extends GenericService<Foto>{
	
	public FotoService() {
		super(Foto.class);
	}

}
