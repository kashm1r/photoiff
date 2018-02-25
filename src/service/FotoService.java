package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import modelo.Foto;

@Stateless
public class FotoService extends GenericService<Foto>{
	
	public FotoService() {
		super(Foto.class);
	}
	
	public List<Foto> obterFotosBanco(){
		TypedQuery<Foto> query = getEntityManager().createQuery("SELECT * FROM Foto", Foto.class);
		List<Foto> lista = query.getResultList();
		
		return lista;
	}
				
}
