package mx.ipn.escom.ars.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import mx.ipn.escom.ars.dao.MuseoDao;
import mx.ipn.escom.ars.modelo.Elemento;
import mx.ipn.escom.ars.modelo.Museo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.jersey.spi.resource.Singleton;

@Path("/museos")
@Component("MuseoResource")
public class MuseoResource{	
	private MuseoDao museoDao;
	
	@GET
	@Transactional
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public List<Museo> getMuseos(){		
					
		List<Museo> museos=museoDao.findAll();
		return museos;
	}
		

	public MuseoDao getMuseoDao() {
		return museoDao;
	}

	public void setMuseoDao(MuseoDao museoDao) {
		this.museoDao = museoDao;
	}
	

}
