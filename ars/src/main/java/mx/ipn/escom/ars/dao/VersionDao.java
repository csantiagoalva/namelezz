package mx.ipn.escom.ars.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.modelo.Version;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("versionDao")
public class VersionDao extends HibernateDaoSupport {
	public List<Version> findAll() {
		return getHibernateTemplate().loadAll(Version.class);
	}

	public Version findById(Integer id) {
		return getHibernateTemplate().get(Version.class, id);
	}

	public Version save(Version entity) {
		if (entity.getId() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Version entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Version> findByExample(Version version) { 
		return getHibernateTemplate().findByExample(version);
	}
	
	public void cambiaVersion(String tabla){
		Version versionAux=new Version();
		versionAux.setTabla(tabla);
		List<Version> versiones=findByExample(versionAux);
		if(versiones.isEmpty()){
			versionAux.setVersion(0);
			save(versionAux);
		}else{
			versiones.get(0).setVersion(versiones.get(0).getVersion()+1);
			save(versiones.get(0));
		}
		
	}
}
