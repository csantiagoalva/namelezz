package mx.ipn.escom.ars.negocio;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import mx.ipn.escom.ars.dao.RecompensaDao;
import mx.ipn.escom.ars.dao.VersionDao;
import mx.ipn.escom.ars.modelo.Recompensa;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("recompensaNegocio")
public class RecompensaNegocio {
	private RecompensaDao recompensaDao;
	private VersionDao versionDao;

	@Transactional
	public List<Recompensa> findAll() {
		return recompensaDao.findAll();
	}

	@Transactional
	public Recompensa findById(Integer id) { 
		return recompensaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Recompensa save(Recompensa entidad) { 
		Recompensa modelo = recompensaDao.save(entidad);
		versionDao.cambiaVersion("Recompensa");
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Recompensa entidad) { 
		recompensaDao.delete(entidad);
		versionDao.cambiaVersion("Recompensa");
	}

	@Transactional
	public List<Recompensa> findByExample(Recompensa recompensa) {
		return recompensaDao.findByExample(recompensa);
	}
	
     
	public RecompensaDao getRecompensaDao() {
		return recompensaDao;
	}

	public void setRecompensaDao(RecompensaDao recompensaDao) {
		this.recompensaDao = recompensaDao;
	}

	public VersionDao getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}

	
	
}
