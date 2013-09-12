package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Logro;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("logroDao")
public class LogroDao extends HibernateDaoSupport {
	public List<Logro> findAll() {
		return getHibernateTemplate().loadAll(Logro.class);
	}

	public Logro findById(Integer id) {
		return getHibernateTemplate().get(Logro.class, id);
	}

	public Logro save(Logro entity) {
		if (entity.getIdLogro() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Logro entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Logro> findByExample(Logro logro) {
		return getHibernateTemplate().findByExample(logro);
	}
}
