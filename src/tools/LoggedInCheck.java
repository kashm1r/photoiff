package tools;


import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import modelo.Pessoa;



@SuppressWarnings("serial")
public class LoggedInCheck implements PhaseListener {
	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	public void afterPhase(PhaseEvent event) {
		final FacesContext context = event.getFacesContext();
		final ExternalContext ec = context.getExternalContext();
		final String viewid = context.getViewRoot().getViewId();
	
		final Pessoa usuario = (Pessoa) ec.getSessionMap().get("usuario");  
		try {
			//System.out.println(usuario.getPerfil());
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		if(!viewid.startsWith("/index") && 
				!viewid.startsWith("/primeiroAcesso") && 
				   usuario == null) {
			
					try {
						ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
					} catch (IOException e) {
						e.printStackTrace();
					}
			System.out.println("Usuario não esta logado");
		}
		
		
	}

	public void beforePhase(PhaseEvent event) {}

}