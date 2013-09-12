package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Exposicion;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("exposicionDao")
public class ExposicionDao extends HibernateDaoSupport {
	public List<Exposicion> findAll() {		
		return getHibernateTemplate().loadAll(Exposicion.class);
	}

	public Exposicion findById(Integer id) {
		return getHibernateTemplate().get(Exposicion.class, id);
	}

	public Exposicion save(Exposicion entity) {
		if (entity.getIdExposicion() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Exposicion entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Exposicion>
	public List<Exposicion> findByExample(Exposicion exposicion) { // TODO:
		return getHibernateTemplate().findByExample(exposicion);
	}
}
