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

import mx.ipn.escom.ars.dao.ElementoLogroDao;
import mx.ipn.escom.ars.modelo.ElementoLogro;

@Path("/elementosLogros")
@Component("ElementoLogroResource")
public class ElementoLogroResource {
	private ElementoLogroDao elementoLogroDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<ElementoLogro> getElementosLogros() throws SQLException {

		List<ElementoLogro> elementosLogros = elementoLogroDao.findAll();
		return elementosLogros;
	}

	public ElementoLogroDao getElementoLogroDao() {
		return elementoLogroDao;
	}

	public void setElementoLogroDao(ElementoLogroDao elementoLogroDao) {
		this.elementoLogroDao = elementoLogroDao;
	}
}
