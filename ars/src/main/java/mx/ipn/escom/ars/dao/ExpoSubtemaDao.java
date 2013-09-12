package mx.ipn.escom.ars.dao;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import mx.ipn.escom.ars.modelo.ExpoSubtema;

@Singleton
@Named("expoSubtemaDao")
public class ExpoSubtemaDao extends HibernateDaoSupport{
	public List<ExpoSubtema> findAll() {
		return getHibernateTemplate().loadAll(ExpoSubtema.class);
	}

	public ExpoSubtema findById(Integer id) {
		return getHibernateTemplate().get(ExpoSubtema.class, id);
	}

	public ExpoSubtema save(ExpoSubtema entity) {
		if (entity.getIdExposicion() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(ExpoSubtema entity) {
		entity = getHibernateTemplate().merge(entity);
//		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ExpoSubtema> findByExample(ExpoSubtema entity) { // TODO:
		return getHibernateTemplate().findByExample(entity);
	}	
}
