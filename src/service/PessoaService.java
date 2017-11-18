package service;



import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.Menu;
import modelo.Pessoa;

@Stateless
public class PessoaService extends GenericService<Pessoa> {
	
	public PessoaService(){
		super(Pessoa.class);
	}	
	
	public Pessoa validarUsuario(String log, String pass){
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<Pessoa> cquery = cb.createQuery(Pessoa.class);
		final Root<Pessoa> root = cquery.from(Pessoa.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();

		condicoes.add(cb.equal(root.get("login"), log));
		condicoes.add(cb.equal(root.get("senha"), pass));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		Pessoa pessoa = null;

		try{
			pessoa = getEntityManager().createQuery(cquery).getSingleResult();
			
			for(Menu m : pessoa.getPerfil().getMenus()) {
				m.getItens().size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
    	
		
		
    	return pessoa;
	}
	

	public List<Pessoa> filtrarPorNome(String filtro){
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<Pessoa> cquery = cb.createQuery(Pessoa.class);
		final Root<Pessoa> root = cquery.from(Pessoa.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();

		Expression<String> path = root.get("nome");
		condicoes.add(cb.like(path, "%"+filtro+"%"));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		List<Pessoa> pessoas = getEntityManager().createQuery(cquery).getResultList();
			    	
    	return pessoas;
	}

}
