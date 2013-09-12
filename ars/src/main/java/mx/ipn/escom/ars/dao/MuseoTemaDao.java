package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.ars.modelo.MuseoTema;


@Singleton
@Named("museoTemaDao")
public class MuseoTemaDao extends HibernateDaoSupport{
	public List<MuseoTema> findAll() {
		return getHibernateTemplate().loadAll(MuseoTema.class);
	}

	public MuseoTema findById(Integer id) {
		return getHibernateTemplate().get(MuseoTema.class, id);
	}

	public MuseoTema save(MuseoTema entity) {
		if (entity.getIdMuseo() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(MuseoTema entity) {
		entity = getHibernateTemplate().merge(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<MuseoTema> findByExample(MuseoTema entity) { // TODO:
		return getHibernateTemplate().findByExample(entity);
	}
	
	
}
