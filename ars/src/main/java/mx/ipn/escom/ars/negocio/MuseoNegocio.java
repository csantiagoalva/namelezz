package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.MuseoDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Exposicion;
import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.negocio.MuseoNegocio;

@Singleton
@Named("museoNegocio")
public class MuseoNegocio {
	private MuseoDao museoDao;
	private ExposicionNegocio exposicionNegocio;
	private VersionDao versionDao;
	
	@Transactional
	public List<Museo> findAll() {
		return museoDao.findAll();
	}

	@Transactional
	public Museo findById(Integer id) { 
		return museoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Museo save(Museo entidad) { 
		Museo modelo = museoDao.save(entidad);
		versionDao.cambiaVersion("Museo");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Museo entidad) {
		museoDao.delete(entidad);
		versionDao.cambiaVersion("Museo");
	}

	@Transactional
	public List<Museo> findByExample(Museo museo) {
		return museoDao.findByExample(museo);
	}

	@Transactional
	public Boolean existe(Museo museo) { 
		Museo museoEjemplo = new Museo();
		List<Museo> museos = null;
		museoEjemplo.setNombre(museo.getNombre());
		museos = findByExample(museoEjemplo);
		if (museos != null && museos.size() > 0) {
			if(!museos.get(0).getIdMuseo().equals(museo.getIdMuseo())){
			return true;
			}
		}
		return false;
	}
	
	@Transactional
	public Boolean ubicacionRepetida(Museo museo){
		Museo prueba=new Museo();
		List<Museo> museos=null;
		
		prueba.setCalle(museo.getCalle());
		prueba.setNumero(museo.getNumero());
		prueba.setCodigo(museo.getCodigo());
		prueba.setColonia(museo.getColonia());
		prueba.setDelegacion(museo.getDelegacion());
		prueba.setEstado(museo.getEstado());		
		museos=findByExample(prueba);		
		
		if(!museos.isEmpty()){			
			if(!museos.get(0).getIdMuseo().equals(museo.getIdMuseo())){
			return true;
			}
		}			
		return false;
	}

	@Transactional
	public void cambiaEstado(Museo museo){
		
		if(museo.getEdoMuseo().equals("Inactivo")){			
			for (Exposicion exposicion : museo.getExposiciones()) {
				exposicion.setEdoExposicion("Inactiva");
				exposicionNegocio.save(exposicion);
			}
		}
		
		if(museo.getEdoMuseo().equals("Activo")){					
			int n=0;
			for (Exposicion exposicion : museo.getExposiciones()) {
				if(exposicion.getEdoExposicion().equals("Inactiva")){
					n++;
				}
			}
			
			if(n==museo.getExposiciones().size()){
				for (Exposicion exposicion : museo.getExposiciones()) {
					exposicion.setEdoExposicion("Activa");
					exposicionNegocio.save(exposicion);
				}
			}
						
		}
		
	}
	
     
	public MuseoDao getMuseoDao() {
		return museoDao;
	}

	public void setMuseoDao(MuseoDao museoDao) {
		this.museoDao = museoDao;
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
