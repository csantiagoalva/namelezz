package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.ExposicionDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Exposicion;

import mx.ipn.escom.ars.negocio.ExposicionNegocio;

@Singleton
@Named("exposicionNegocio")
public class ExposicionNegocio {
	private ExposicionDao exposicionDao;
	private VersionDao versionDao;
	
	@Transactional
	public List<Exposicion> findAll() {
		return exposicionDao.findAll();
	}

	@Transactional
	public Exposicion findById(Integer id) { 		
		return exposicionDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Exposicion save(Exposicion entidad) { 
		Exposicion modelo = exposicionDao.save(entidad);
		versionDao.cambiaVersion("Exposicion");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Exposicion entidad) { 
		exposicionDao.delete(entidad);
		versionDao.cambiaVersion("Exposicion");
	}

	@Transactional
	public List<Exposicion> findByExample(Exposicion exposicion) {
		return exposicionDao.findByExample(exposicion);
	}

	@Transactional
	public Boolean existe(Exposicion expo) { 
		Exposicion expoEjemplo = new Exposicion();
		List<Exposicion> expos = null;
		expoEjemplo.setNombre(expo.getNombre());
		expos = findByExample(expoEjemplo);
		if (expos != null && expos.size() > 0) {
			if(!expos.get(0).getIdExposicion().equals(expo.getIdExposicion())){
			return true;
			}
		}
		return false;
	}	
	
     
	public ExposicionDao getExposicionDao() {
		return exposicionDao;
	}

	public void setExposicionDao(ExposicionDao exposicionDao) {
		this.exposicionDao = exposicionDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}
	
	
}
