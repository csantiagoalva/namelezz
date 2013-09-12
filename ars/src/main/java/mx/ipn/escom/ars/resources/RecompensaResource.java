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

import mx.ipn.escom.ars.dao.RecompensaDao;
import mx.ipn.escom.ars.modelo.Recompensa;

@Path("/recompensas")
@Component("RecompensaResource")
public class RecompensaResource {
	private RecompensaDao recompensaDao;

	@GET
	@Transactional
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Recompensa> getRecompensas() {

		List<Recompensa> recompensas = recompensaDao.findAll();

		return recompensas;
	}

	public RecompensaDao getRecompensaDao() {
		return recompensaDao;
	}

	public void setRecompensaDao(RecompensaDao recompensaDao) {
		this.recompensaDao = recompensaDao;
	}
}
