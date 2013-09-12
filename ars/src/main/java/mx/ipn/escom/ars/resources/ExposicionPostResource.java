package mx.ipn.escom.ars.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mx.ipn.escom.ars.dao.ExposicionDao;
import mx.ipn.escom.ars.modelo.Exposicion;
import mx.ipn.escom.ars.negocio.ExposicionNegocio;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

@Path("/exposicionesPost")
@Component("ExposicionPostResource")
public class ExposicionPostResource {
	private ExposicionNegocio exposicionNegocio;
	private Exposicion exposicion;
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveCalif(JSONArray jsonArray) throws JSONException {
		String result="Recibido";
		System.out.println("En post");
		for(int i=0;i<jsonArray.length();i++){
			JSONObject jo=(JSONObject) jsonArray.get(i);
			
			Integer id=jo.getInt("idExpo");
			Integer rank=jo.getInt("rank");
			Integer env=jo.getInt("env");
			System.out.println(id+" , "+rank+" , "+env);
			
//			Exposicion exposicion=new Exposicion();
			exposicion=exposicionNegocio.findById(id);
			
			System.out.println("exposicion encontrada "+exposicion.getNombre());
									
			if(exposicion.getRanking()==null){
				exposicion.setRanking(0.0);
			}
			
			if(exposicion.getNumCalificaciones()==null){
				exposicion.setNumCalificaciones(0);
			}
			
			Integer calificaciones=exposicion.getNumCalificaciones();
			Double ranking=exposicion.getRanking();
			
			exposicion.setRanking(((calificaciones*ranking)+rank)/(calificaciones+1));
			
			exposicion.setNumCalificaciones(calificaciones+1);
			
			exposicionNegocio.save(exposicion);
			
		}							
		return Response.status(201).entity(result).build();		
	}

	
	
	public ExposicionNegocio getExposicionNegocio() {
		return exposicionNegocio;
	}

	public void setExposicionNegocio(ExposicionNegocio exposicionNegocio) {
		this.exposicionNegocio = exposicionNegocio;
	}



	public Exposicion getExposicion() {
		return exposicion;
	}



	public void setExposicion(Exposicion exposicion) {
		this.exposicion = exposicion;
	}		 


}
