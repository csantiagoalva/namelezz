package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;



import mx.ipn.escom.ars.dao.MuseoTemaDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.MuseoTema;

import org.springframework.transaction.annotation.Transactional;
@Singleton
@Named("museoTemaNegocio")
public class MuseoTemaNegocio {
	private MuseoTemaDao museoTemaDao;
	private VersionDao versionDao;

	@Transactional
	public List<MuseoTema> findAll() {
		return museoTemaDao.findAll();
	}

	@Transactional
	public MuseoTema findById(Integer id) {
		return museoTemaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public MuseoTema save(MuseoTema entidad) {
		MuseoTema modelo = museoTemaDao.save(entidad);
		versionDao.cambiaVersion("MuseoTema");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(MuseoTema entidad) {
		museoTemaDao.delete(entidad);
		versionDao.cambiaVersion("MuseoTema");
	}

	public MuseoTemaDao getEstadoDao() {
		return museoTemaDao;
	}

	public void setaccionDao(MuseoTemaDao museoTemaDao) {
		this.museoTemaDao = museoTemaDao;
	}

	@Transactional
	public List<MuseoTema> findByExample(MuseoTema museoTema) {
		return museoTemaDao.findByExample(museoTema);
	}

	public MuseoTemaDao getMuseoTemaDao() {
		return museoTemaDao;
	}

	public void setMuseoTemaDao(MuseoTemaDao museoTemaDao) {
		this.museoTemaDao = museoTemaDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}


}
