package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Tema;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("temaDao")
public class TemaDao extends HibernateDaoSupport {
	public List<Tema> findAll() {		
		return getHibernateTemplate().loadAll(Tema.class);
	}

	public Tema findById(Integer id) {
		return getHibernateTemplate().get(Tema.class, id);
	}

	public Tema save(Tema entity) {
		if (entity.getIdTema() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Tema entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Tema> findByExample(Tema tema) { // TODO:
		return getHibernateTemplate().findByExample(tema);
	}
}
