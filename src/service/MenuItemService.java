package service;

import javax.ejb.Stateless;
import modelo.MenuItem;

@Stateless
public class MenuItemService extends GenericService<MenuItem>{
	
	public MenuItemService() {
		super(MenuItem.class);
	}
	
}
