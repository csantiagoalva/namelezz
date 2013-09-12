package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.TemaDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Subtema;
import mx.ipn.escom.ars.modelo.Tema;
import mx.ipn.escom.ars.negocio.TemaNegocio;

@Singleton
@Named("temaNegocio")
public class TemaNegocio {
	private TemaDao temaDao;
	private VersionDao versionDao;
	
	@Transactional
	public List<Tema> findAll() {
		return temaDao.findAll();
	}

	@Transactional
	public Tema findById(Integer id) {
		return temaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Tema save(Tema entidad) {
		Tema modelo = temaDao.save(entidad);
		versionDao.cambiaVersion("Tema");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Tema entidad) {
		versionDao.cambiaVersion("Tema");
		temaDao.delete(entidad);
	}

	@Transactional
	public List<Tema> findByExample(Tema tema) {
		return temaDao.findByExample(tema);
	}

	@Transactional
	public Boolean existe(String nombre) { 
		Tema temaEjemplo = new Tema();
		List<Tema> temas = null;
		temaEjemplo.setNombre(nombre);
		temas = findByExample(temaEjemplo);
		if (temas != null && temas.size() > 0) {
			return true;
		}
		return false;
	}
	
     
	public TemaDao getTemaDao() {
		return temaDao;
	}

	public void setTemaDao(TemaDao temaDao) {
		this.temaDao = temaDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}
	
	
}
