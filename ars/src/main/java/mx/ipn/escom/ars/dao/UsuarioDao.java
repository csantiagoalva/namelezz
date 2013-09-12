package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Usuario;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("usuarioDao")
public class UsuarioDao extends HibernateDaoSupport{
	
	public List<Usuario> findAll() {
		return getHibernateTemplate().loadAll(Usuario.class);
	}

	public Usuario findById(Integer id) {
		return getHibernateTemplate().get(Usuario.class, id);
	}

	public Usuario save(Usuario entity) {
		if (entity.getIdUsuario() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Usuario entity) {
		entity = getHibernateTemplate().merge(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findByExample(Usuario usuario) { 
		return getHibernateTemplate().findByExample(usuario);
	}

}
