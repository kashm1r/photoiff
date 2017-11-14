package service;



import javax.ejb.Stateless;
import modelo.Pessoa;

@Stateless
public class PessoaService extends GenericService<Pessoa> {
	
	public PessoaService(){
		super(Pessoa.class);
	}	

}
