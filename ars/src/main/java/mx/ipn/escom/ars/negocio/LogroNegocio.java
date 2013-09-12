package mx.ipn.escom.ars.negocio;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import mx.ipn.escom.ars.dao.LogroDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Elemento;
import mx.ipn.escom.ars.modelo.Exposicion;
import mx.ipn.escom.ars.modelo.Logro;
import mx.ipn.escom.ars.modelo.Museo;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("logroNegocio")
public class LogroNegocio {
	private LogroDao logroDao;	
	private ExposicionNegocio exposicionNegocio;	
	private VersionDao	versionDao;
	
	@Transactional
	public List<Logro> findAll() {
		return logroDao.findAll();
	}

	@Transactional
	public Logro findById(Integer id) { 
		return logroDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Logro save(Logro entidad) { 
		Logro modelo = logroDao.save(entidad);
		versionDao.cambiaVersion("Logro");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Logro entidad) {
		logroDao.delete(entidad);
		versionDao.cambiaVersion("Logro");
	}

	@Transactional
	public List<Logro> findByExample(Logro logro) {
		return logroDao.findByExample(logro);
	}

	@Transactional
	public List<Logro> findByMuseo(Museo museo) {
		List<Logro> list=new ArrayList<Logro>();
		Exposicion expoAux = new Exposicion();
		expoAux.setIdMuseo(museo.getIdMuseo());

		List<Exposicion> expos = exposicionNegocio.findByExample(expoAux);
		List<Elemento> elementosMuseo = new ArrayList<Elemento>();

		for (Exposicion exp : expos) {
			elementosMuseo.addAll(exp.getElementos());			
		}		

		for (Elemento el : elementosMuseo) {
			if (!el.getLogros().isEmpty()) {
				for (Logro lg : el.getLogros()) {
					if (!list.contains(lg)) {
						list.add(lg);
					}
				}
			}
		}
	return list;
	}

	public LogroDao getLogroDao() {
		return logroDao;
	}

	public void setLogroDao(LogroDao logroDao) {
		this.logroDao = logroDao;
	}

	public ExposicionNegocio getExposicionNegocio() {
		return exposicionNegocio;
	}

	public void setExposicionNegocio(ExposicionNegocio exposicionNegocio) {
		this.exposicionNegocio = exposicionNegocio;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}

}
