package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.Pessoa;

@Stateless
public class PessoaService extends GenericService<Pessoa> {
	
	public PessoaService(){
		super(Pessoa.class);
	}
	
	//função para poder localizar os usuários por nome
	public List<Pessoa> filtrarUsuarioPorNome(String filtro){
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<Pessoa> cquery = cb.createQuery(Pessoa.class);
		final Root<Pessoa> root = cquery.from(Pessoa.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();
		
		Expression<String> path = root.get("nome");
		condicoes.add(cb.like(path, "%"+filtro+"%"));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		List<Pessoa> usuarios = getEntityManager().createQuery(cquery).getResultList();
		
		return usuarios;

	}
	
	//função para poder localizar os administradores por nome
		public List<Pessoa> filtrarAdministradorPorNome(String filtro){
			final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			
			final CriteriaQuery<Pessoa> cquery = cb.createQuery(Pessoa.class);
			final Root<Pessoa> root = cquery.from(Pessoa.class);
			final List<Predicate> condicoes = new ArrayList<Predicate>();
			
			Expression<String> path = root.get("nome");
			condicoes.add(cb.like(path, "%"+filtro+"%"));
			
			cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
			List<Pessoa> administradores = getEntityManager().createQuery(cquery).getResultList();
			
			return administradores;
		}	

}
