package mx.ipn.escom.ars.negocio;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import mx.ipn.escom.ars.dao.ElementoLogroDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.ElementoLogro;
import mx.ipn.escom.ars.modelo.Version;

import org.springframework.transaction.annotation.Transactional;
@Singleton
@Named("elementoLogroNegocio")
public class ElementoLogroNegocio {
	private ElementoLogroDao elementoLogroDao;
	private VersionDao versionDao;

	@Transactional
	public List<ElementoLogro> findAll() {
		return elementoLogroDao.findAll();
	}

	@Transactional
	public ElementoLogro findById(Integer id) {
		return elementoLogroDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public ElementoLogro save(ElementoLogro entidad) {
		ElementoLogro modelo = elementoLogroDao.save(entidad);
				
		versionDao.cambiaVersion("ElementoLogro");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(ElementoLogro entidad) {
		elementoLogroDao.delete(entidad);
		versionDao.cambiaVersion("ElementoLogro");
	}

	@Transactional
	public List<ElementoLogro> findByExample(ElementoLogro elementoLogro) {
		return elementoLogroDao.findByExample(elementoLogro);
	}

	public ElementoLogroDao getElementoLogroDao() {
		return elementoLogroDao;
	}

	public void setElementoLogroDao(ElementoLogroDao elementoLogroDao) {
		this.elementoLogroDao = elementoLogroDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}


}
