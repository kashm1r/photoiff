package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
	

import modelo.Menu;

@Stateless
public class MenuService extends GenericService<Menu>{
	
	public MenuService() {
		super(Menu.class);
	}
	
	public List<Menu> obtemMenuComMenuItens(){
		
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Menu> cQuery = cb.createQuery(Menu.class);
		
		cQuery.select(cQuery.from(Menu.class));
		
		List<Menu> menu = getEntityManager().createQuery(cQuery).getResultList();
		
		for (Menu m : menu) {
			m.getItens().size();
		}
		
		return menu;
 	}


}
