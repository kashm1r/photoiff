package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import modelo.Perfil;

@Stateless
public class PerfilService extends GenericService<Perfil>{
	
	public PerfilService(){
		super(Perfil.class);
	}
	
	public List<Perfil> obtemPerfilComMenus(){
		
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Perfil> cQuery = cb.createQuery(Perfil.class);
		
		cQuery.select(cQuery.from(Perfil.class));
		
		List<Perfil> perfis = getEntityManager().createQuery(cQuery).getResultList();
		
		for (Perfil p : perfis) {
			p.getMenus().size();
		}
		
		return perfis;
	}
	
}
