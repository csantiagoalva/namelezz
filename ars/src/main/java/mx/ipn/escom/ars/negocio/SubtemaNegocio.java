package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.SubtemaDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.modelo.Subtema;
import mx.ipn.escom.ars.negocio.SubtemaNegocio;

@Singleton
@Named("subtemaNegocio")
public class SubtemaNegocio {
	private SubtemaDao subtemaDao;
	private VersionDao versionDao;
	
	@Transactional
	public List<Subtema> findAll() {
		return subtemaDao.findAll();
	}

	@Transactional
	public Subtema findById(Integer id) { 
		return subtemaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Subtema save(Subtema entidad) {
		Subtema modelo = subtemaDao.save(entidad);
		versionDao.cambiaVersion("Subtema");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Subtema entidad) { 
		subtemaDao.delete(entidad);
		versionDao.cambiaVersion("Subtema");
	}

	@Transactional
	public List<Subtema> findByExample(Subtema subtema) {
		return subtemaDao.findByExample(subtema);
	}
		
	@Transactional
	public Boolean existe(String nombre) { // TODO:
		Subtema subtemaEjemplo = new Subtema();
		List<Subtema> subtemas = null;
		subtemaEjemplo.setNombre(nombre);
		subtemas = findByExample(subtemaEjemplo);
		if (subtemas != null && subtemas.size() > 0) {
			return true;
		}
		return false;
	}
	
	public SubtemaDao getSubtemaDao() {
		return subtemaDao;
	}

	public void setSubtemaDao(SubtemaDao subtemaDao) {
		this.subtemaDao = subtemaDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}
	
	
	
}
