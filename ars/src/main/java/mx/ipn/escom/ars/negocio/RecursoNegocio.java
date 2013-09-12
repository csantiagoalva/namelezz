package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.RecursoDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Recurso;
import mx.ipn.escom.ars.negocio.RecursoNegocio;

@Singleton
@Named("recursoNegocio")
public class RecursoNegocio {
	private RecursoDao recursoDao;
	private VersionDao	versionDao;
	
	@Transactional
	public List<Recurso> findAll() {
		return recursoDao.findAll();
	}

	@Transactional
	public Recurso findById(Integer id) {
		return recursoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Recurso save(Recurso entidad) {
		Recurso modelo = recursoDao.save(entidad);
		versionDao.cambiaVersion("Recurso");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Recurso entidad) { 
		recursoDao.delete(entidad);
		versionDao.cambiaVersion("Recurso");
	}

	@Transactional
	public List<Recurso> findByExample(Recurso recurso) {
		return recursoDao.findByExample(recurso);
	}	
	
     
	public RecursoDao getRecursoDao() {
		return recursoDao;
	}

	public void setRecursoDao(RecursoDao recursoDao) {
		this.recursoDao = recursoDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}
	
	
}
