package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Museo;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("museoDao")
public class MuseoDao extends HibernateDaoSupport {			
	
	public List<Museo> findAll() {		         
		return getHibernateTemplate().loadAll(Museo.class);
	}

	public Museo findById(Integer id) {
		return getHibernateTemplate().get(Museo.class, id);
	}

	public Museo save(Museo entity) {
		if (entity.getIdMuseo() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Museo entity) {
		getHibernateTemplate().merge(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Museo> findByExample(Museo museo) {
		return getHibernateTemplate().findByExample(museo);
	}	
	
	
		
}
