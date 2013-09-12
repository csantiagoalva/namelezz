package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Elemento;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("elementoDao")
public class ElementoDao extends HibernateDaoSupport {
	public List<Elemento> findAll() {
		return getHibernateTemplate().loadAll(Elemento.class);
	}

	public Elemento findById(Integer id) {
		return getHibernateTemplate().get(Elemento.class, id);
	}

	public Elemento save(Elemento entity) {
		if (entity.getIdElemento() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Elemento entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Elemento> findByExample(Elemento elemento) { 
		return getHibernateTemplate().findByExample(elemento);
	}
}
