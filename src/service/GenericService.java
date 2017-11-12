package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.QueryTimeoutException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



import modelo.Pessoa;

@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class GenericService<T> {
	
	@PersistenceContext(unitName="punit")
    private EntityManager entityManager;
	
	private final Class<T> classe;

	public GenericService(Class<T> classe) {
		this.classe = classe;        
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void create(T entity ) {
        getEntityManager().persist(entity); 
    }

    public void merge(T entity){
			entity = getEntityManager().merge(entity);			
    }

    //verificar
    public void remove(T entity) {
	        getEntityManager().remove(getEntityManager().merge(entity));		  
    }
    
    public final T obtemPorId(Long id ){
		T entity = getEntityManager().find(classe, id);		
		return entity;
	}
	
    
    public List<T> listAll(){
    	final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();	
    	final CriteriaQuery<T> cQuery = cb.createQuery(classe);
	
    	cQuery.select(cQuery.from(classe));

    	List<T> list = getEntityManager().createQuery(cQuery).getResultList();
    	return list;
    }
    
	//fun��o para poder verificar se o usuario que est� logando tem os dados corretos
	public Pessoa validarLoginAdminstrador(String login, String senha){
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<Pessoa> cquery = cb.createQuery(Pessoa.class);
		final Root<Pessoa> root = cquery.from(Pessoa.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();
		
		condicoes.add(cb.equal(root.get("usuario").get("matricula"), login));
		condicoes.add(cb.equal(root.get("usuario").get("senha"),senha));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		Pessoa pessoa = new Pessoa();
		try{
			pessoa = getEntityManager().createQuery(cquery).getSingleResult();
		}catch (Exception e) {
			throw new QueryTimeoutException("Matricula ou Senha Invalidas");
		}
		
		return pessoa;
	}
}


