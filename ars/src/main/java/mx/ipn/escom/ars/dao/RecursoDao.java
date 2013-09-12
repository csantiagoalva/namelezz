package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.modelo.Recurso;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("recursoDao")
public class RecursoDao extends HibernateDaoSupport {
	public List<Recurso> findAll() {
		return getHibernateTemplate().loadAll(Recurso.class);
	}

	public Recurso findById(Integer id) {
		return getHibernateTemplate().get(Recurso.class, id);
	}

	public Recurso save(Recurso entity) {
		if (entity.getIdRecurso() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Recurso entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Recurso> findByExample(Recurso recurso) { // TODO:
		return getHibernateTemplate().findByExample(recurso);
	}
}
