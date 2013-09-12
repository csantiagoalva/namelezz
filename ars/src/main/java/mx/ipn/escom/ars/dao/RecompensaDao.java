package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Recompensa;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("recompensaDao")
public class RecompensaDao extends HibernateDaoSupport {
	public List<Recompensa> findAll() {
		return getHibernateTemplate().loadAll(Recompensa.class);
	}

	public Recompensa findById(Integer id) {
		return getHibernateTemplate().get(Recompensa.class, id);
	}

	public Recompensa save(Recompensa entity) {
		if (entity.getIdLogro() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Recompensa entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked") 
	public List<Recompensa> findByExample(Recompensa recompensa) {
		return getHibernateTemplate().findByExample(recompensa);
	}
}
