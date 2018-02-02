package service;

import javax.ejb.Stateless;

import modelo.Tag;


@Stateless
public class TagService extends GenericService<Tag>{
	
	public TagService() {
		super(Tag.class);
	}

}
