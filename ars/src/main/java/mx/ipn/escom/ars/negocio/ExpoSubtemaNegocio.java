package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.ars.dao.ExpoSubtemaDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.ExpoSubtema;

import org.springframework.transaction.annotation.Transactional;
@Singleton
@Named("expoSubtemaNegocio")
public class ExpoSubtemaNegocio {
	private ExpoSubtemaDao expoSubtemaDao;
	private VersionDao versionDao;
	@Transactional
	public List<ExpoSubtema> findAll() {
		return expoSubtemaDao.findAll();
	}

	@Transactional
	public ExpoSubtema findById(Integer id) { 
		return expoSubtemaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public ExpoSubtema save(ExpoSubtema entidad) {
		ExpoSubtema modelo = expoSubtemaDao.save(entidad);
		versionDao.cambiaVersion("ExposicionSubtema");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(ExpoSubtema entidad) { 
		expoSubtemaDao.delete(entidad);
		versionDao.cambiaVersion("ExposicionSubtema");
	}

	@Transactional
	public List<ExpoSubtema> findByExample(ExpoSubtema expoSubtema) {
		return expoSubtemaDao.findByExample(expoSubtema);
	}
		
     
	public ExpoSubtemaDao getExpoSubtemaDao() {
		return expoSubtemaDao;
	}

	public void setExpoSubtemaDao(ExpoSubtemaDao expoSubtemaDao) {
		this.expoSubtemaDao = expoSubtemaDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}


}
