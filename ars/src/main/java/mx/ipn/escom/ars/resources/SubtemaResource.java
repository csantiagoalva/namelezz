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

import mx.ipn.escom.ars.dao.SubtemaDao;
import mx.ipn.escom.ars.modelo.Subtema;

@Path("/subtemas")
@Component("SubtemaResource")
public class SubtemaResource {
	private SubtemaDao subtemaDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Subtema> getSubtemas() throws SQLException {

		List<Subtema> subtemas = subtemaDao.findAll();

		return subtemas;
	}

	public SubtemaDao getSubtemaDao() {
		return subtemaDao;
	}

	public void setSubtemaDao(SubtemaDao subtemaDao) {
		this.subtemaDao = subtemaDao;
	}
}
