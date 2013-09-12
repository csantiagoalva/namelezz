package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Subtema;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("subtemaDao")
public class SubtemaDao extends HibernateDaoSupport {
	public List<Subtema> findAll() {		
		return getHibernateTemplate().loadAll(Subtema.class);
	}

	public Subtema findById(Integer id) {
		return getHibernateTemplate().get(Subtema.class, id);
	}

	public Subtema save(Subtema entity) {
		if (entity.getIdSubtema() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Subtema entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Subtema>
	public List<Subtema> findByExample(Subtema subtema) { // TODO:
		return getHibernateTemplate().findByExample(subtema);
	}
}
