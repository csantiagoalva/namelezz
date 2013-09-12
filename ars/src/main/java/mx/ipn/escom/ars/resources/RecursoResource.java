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

import mx.ipn.escom.ars.dao.RecursoDao;
import mx.ipn.escom.ars.modelo.Recurso;

@Path("/recursos")
@Component("RecursoResource")
public class RecursoResource {
	private RecursoDao recursoDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Recurso> getRecursos() throws SQLException {

		List<Recurso> recursos = recursoDao.findAll();
		return recursos;
	}

	public RecursoDao getRecursoDao() {
		return recursoDao;
	}

	public void setRecursoDao(RecursoDao recursoDao) {
		this.recursoDao = recursoDao;
	}
}
