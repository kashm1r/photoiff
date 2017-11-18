package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.QueryTimeoutException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.Perfil;
import modelo.Pessoa;

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
	
	
	public Pessoa validarUsuario(String log, String pass){
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<Pessoa> cquery = cb.createQuery(Pessoa.class);
		final Root<Pessoa> root = cquery.from(Pessoa.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();

		condicoes.add(cb.equal(root.get("usuario").get("login"), log));
		condicoes.add(cb.equal(root.get("usuario").get("senha"), pass));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		Pessoa pessoa = new Pessoa();
		try{
			pessoa = getEntityManager().createQuery(cquery).getSingleResult();
		} catch (Exception e) {
			throw new QueryTimeoutException("Usuário ou senha invalido!");
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
