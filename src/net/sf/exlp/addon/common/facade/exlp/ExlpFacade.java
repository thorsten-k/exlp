package net.sf.exlp.addon.common.facade.exlp;

import java.util.List;

import net.sf.exlp.addon.common.facade.ExlpIntegrityException;

public interface ExlpFacade
{
	Object newObject(Object o);
	
	Object findObject(Class<?> c,int id);
	Object findObject(Class<?> c,long id);
	List<?> findObjects(String tableName);

	void deleteObject(Object o) throws ExlpIntegrityException;
	Object updateObject(Object o);
	
	void checkout();
}
