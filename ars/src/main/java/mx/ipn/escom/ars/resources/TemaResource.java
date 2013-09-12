package mx.ipn.escom.ars.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import mx.ipn.escom.ars.dao.TemaDao;
import mx.ipn.escom.ars.modelo.Tema;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.spi.resource.Singleton;

@Path("/temas")
@Component("TemaResource")
public class TemaResource {	
		
	private TemaDao temaDao;
		
	@GET
	@Transactional
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Tema> getTemas() throws SQLException{
			
		List<Tema> temas=temaDao.findAll();
		
		
		return temas;
	}

	public TemaDao getTemaDao() {
		return temaDao;
	}

	public void setTemaDao(TemaDao temaDao) {
		this.temaDao = temaDao;
	}
}
