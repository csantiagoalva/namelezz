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

import mx.ipn.escom.ars.dao.LogroDao;
import mx.ipn.escom.ars.modelo.Logro;

@Path("/logros")
@Component("LogroResource")
public class LogroResource {
	private LogroDao logroDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Logro> getLogros() {

		List<Logro> logros = logroDao.findAll();
		return logros;
	}

	public LogroDao getLogroDao() {
		return logroDao;
	}

	public void setLogroDao(LogroDao logroDao) {
		this.logroDao = logroDao;
	}
}
