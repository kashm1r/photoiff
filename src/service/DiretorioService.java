package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Diretorio;

@Stateless
public class DiretorioService extends GenericService<Diretorio> {

	public DiretorioService() {
		super(Diretorio.class);
	}

	public List<Diretorio> raizesComNos() {

		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Diretorio> cQuery = cb.createQuery(Diretorio.class);
		final Root<Diretorio> root = cQuery.from(Diretorio.class);
		
		cQuery.select(root).where(cb.isNull(root.get("diretorioPai")));

		List<Diretorio> diretorios = getEntityManager().createQuery(cQuery).getResultList();
		

		return diretorios;
	}
	
	
	public List<Diretorio> obtemNos(Diretorio diretorioPai) {

		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Diretorio> cQuery = cb.createQuery(Diretorio.class);
		final Root<Diretorio> root = cQuery.from(Diretorio.class);
		
		cQuery.select(root).where(cb.equal(root.get("diretorioPai"), diretorioPai));

		List<Diretorio> diretorios = getEntityManager().createQuery(cQuery).getResultList();


		return diretorios;
	}

}
