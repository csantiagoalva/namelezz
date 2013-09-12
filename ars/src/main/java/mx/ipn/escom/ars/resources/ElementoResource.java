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

import mx.ipn.escom.ars.dao.ElementoDao;
import mx.ipn.escom.ars.modelo.Elemento;

@Path("/elementos")
@Component("ElementoResource")
public class ElementoResource {
	private ElementoDao elementoDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Elemento> getElementos() throws SQLException {

		List<Elemento> elementos = elementoDao.findAll();
		return elementos;
	}

	public ElementoDao getElementoDao() {
		return elementoDao;
	}

	public void setElementoDao(ElementoDao elementoDao) {
		this.elementoDao = elementoDao;
	}
}
