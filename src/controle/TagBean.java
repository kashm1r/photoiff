package controle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Tag;
import service.TagService;

@ViewScoped
@ManagedBean
public class TagBean {
	
	@EJB
	TagService tagService;
	
	private Tag tag = new Tag();

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	

}
