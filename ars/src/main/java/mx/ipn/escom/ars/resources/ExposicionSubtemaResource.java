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

import mx.ipn.escom.ars.dao.ExpoSubtemaDao;
import mx.ipn.escom.ars.modelo.ExpoSubtema;

@Path("/exposicionesSubtemas")
@Component("ExposicionSubtemaResource")
public class ExposicionSubtemaResource {
	private ExpoSubtemaDao expoSubtemaDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<ExpoSubtema> getExposicionesSubtemas() throws SQLException {

		List<ExpoSubtema> exposicionesSubtemas = expoSubtemaDao.findAll();
		return exposicionesSubtemas;
	}

	public ExpoSubtemaDao getExpoSubtemaDao() {
		return expoSubtemaDao;
	}

	public void setExpoSubtemaDao(ExpoSubtemaDao expoSubtemaDao) {
		this.expoSubtemaDao = expoSubtemaDao;
	}
}
