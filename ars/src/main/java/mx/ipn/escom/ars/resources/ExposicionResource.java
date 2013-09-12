package mx.ipn.escom.ars.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.ars.dao.ExposicionDao;
import mx.ipn.escom.ars.modelo.Exposicion;

@Path("/exposiciones")
@Component("ExposicionResource")
public class ExposicionResource {
	private ExposicionDao exposicionDao;

	@Transactional
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Exposicion> getExposiciones() throws SQLException {
		List<Exposicion> exposiciones = exposicionDao.findAll();

		return exposiciones;
	}
	
	public ExposicionDao getExposicionDao() {
		return exposicionDao;
	}

	public void setExposicionDao(ExposicionDao exposicionDao) {
		this.exposicionDao = exposicionDao;
	}
}
