package mx.ipn.escom.ars.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.MuseoTemaDao;
import mx.ipn.escom.ars.modelo.MuseoTema;

@Path("/museosTemas")
@Component("MuseoTemaResource")
public class MuseoTemaResource {
	private MuseoTemaDao museoTemaDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<MuseoTema> getMuseosTemas() throws SQLException {

		List<MuseoTema> museosTemas = museoTemaDao.findAll();
		return museosTemas;
	}

	public MuseoTemaDao getMuseoTemaDao() {
		return museoTemaDao;
	}

	public void setMuseoTemaDao(MuseoTemaDao museoTemaDao) {
		this.museoTemaDao = museoTemaDao;
	}
}
