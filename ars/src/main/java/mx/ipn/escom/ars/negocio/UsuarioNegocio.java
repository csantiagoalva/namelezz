package mx.ipn.escom.ars.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.UsuarioDao;
import mx.ipn.escom.ars.modelo.Usuario;

@Singleton
@Named("usuarioNegocio")
public class UsuarioNegocio {

	private UsuarioDao usuarioDao;
	
	@Transactional
	public List<Usuario> findAll() {
		System.out.println("buscando");
		return usuarioDao.findAll();
	}

	@Transactional
	public Usuario findById(Integer id) { 
		return usuarioDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Usuario save(Usuario entidad) {
		Usuario modelo = usuarioDao.save(entidad);
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Usuario entidad) { 
		usuarioDao.delete(entidad);
	}

	@Transactional
	public List<Usuario> findByExample(Usuario usuario) {		
		return usuarioDao.findByExample(usuario);
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	
}
