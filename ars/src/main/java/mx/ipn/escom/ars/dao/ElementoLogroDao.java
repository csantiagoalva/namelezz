package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.ars.modelo.ElementoLogro;

@Singleton
@Named("elementoLogroDao")
public class ElementoLogroDao extends HibernateDaoSupport{
	public List<ElementoLogro> findAll() {
		return getHibernateTemplate().loadAll(ElementoLogro.class);
	}

	public ElementoLogro findById(Integer id) {
		return getHibernateTemplate().get(ElementoLogro.class, id);
	}

	public ElementoLogro save(ElementoLogro entity) {
		if (entity.getIdElemento() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(ElementoLogro entity) {
		entity = getHibernateTemplate().merge(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ElementoLogro> findByExample(ElementoLogro entity) {
		return getHibernateTemplate().findByExample(entity);
	}
	
	
}
