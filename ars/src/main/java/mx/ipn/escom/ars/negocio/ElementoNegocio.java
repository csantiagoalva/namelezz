package mx.ipn.escom.ars.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.ElementoDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Elemento;
import mx.ipn.escom.ars.modelo.Exposicion;
import mx.ipn.escom.ars.modelo.Logro;
import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.negocio.ElementoNegocio;

@Singleton
@Named("elementoNegocio")
public class ElementoNegocio {
	private ElementoDao elementoDao;
	private ExposicionNegocio exposicionNegocio;
	private VersionDao versionDao;
	
	@Transactional
	public List<Elemento> findAll() {
		System.out.println("buscando");
		return elementoDao.findAll();
	}

	@Transactional
	public Elemento findById(Integer id) { 
		return elementoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Elemento save(Elemento entidad) {
		Elemento modelo = elementoDao.save(entidad);
		versionDao.cambiaVersion("Elemento");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Elemento entidad) { 
		elementoDao.delete(entidad);
		versionDao.cambiaVersion("Elemento");
	}

	@Transactional
	public List<Elemento> findByExample(Elemento elemento) {
		return elementoDao.findByExample(elemento);
	}
	
	@Transactional
	public List<Elemento> findByMuseo(Museo museo) {		
		Exposicion expoAux = new Exposicion();		
		expoAux.setIdMuseo(museo.getIdMuseo());

		List<Exposicion> expos = exposicionNegocio.findByExample(expoAux);
		List<Elemento> elementosMuseo = new ArrayList<Elemento>();

		for (Exposicion exp : expos) {
			elementosMuseo.addAll(exp.getElementos());			
		}		

		return elementosMuseo;
	}

	public ElementoDao getElementoDao() {
		return elementoDao;
	}

	public void setElementoDao(ElementoDao elementoDao) {
		this.elementoDao = elementoDao;
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
