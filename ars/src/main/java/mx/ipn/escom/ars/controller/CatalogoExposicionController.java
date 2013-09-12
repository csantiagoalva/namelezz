package mx.ipn.escom.ars.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.ars.modelo.ExpoSubtema;
import mx.ipn.escom.ars.modelo.ExpoSubtemaId;
import mx.ipn.escom.ars.modelo.Exposicion;
import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.modelo.MuseoTema;
import mx.ipn.escom.ars.modelo.MuseoTemaId;
import mx.ipn.escom.ars.modelo.Subtema;
import mx.ipn.escom.ars.modelo.Tema;

import mx.ipn.escom.ars.negocio.ExpoSubtemaNegocio;
import mx.ipn.escom.ars.negocio.ExposicionNegocio;
import mx.ipn.escom.ars.negocio.MuseoNegocio;
import mx.ipn.escom.ars.negocio.SubtemaNegocio;
import mx.ipn.escom.ars.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-exposicion" }) })
public class CatalogoExposicionController extends ActionSupport implements
		ModelDriven<Exposicion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2160127868362247033L;
	private Exposicion model;
	private Museo museo;
	private ExposicionNegocio exposicionNegocio;
	private List<Exposicion> list;
	private Integer idSel;
	private Integer idMuseoSel;
	private List<Museo> museos;
	private List<Subtema> subtemas;	
	private Subtema subtema;
	private MuseoNegocio museoNegocio;
	private SubtemaNegocio subtemaNegocio;
	private ExpoSubtemaNegocio expoSubtemaNegocio;

	private List<Subtema> subtemasRegistrados;
	private List<Subtema> subtemasExpo;
	private List<Integer> idSubtemasSeleccionados = new ArrayList<Integer>();
	private List<Integer> idSubtemasDisponibles = new ArrayList<Integer>();

	private List<String> type = new ArrayList<String>();
	private List<String> edo = new ArrayList<String>();
	
	private File fileDat = null;
	private String filenameDat;
	private String contentTypeDat;
	
	private File fileXml = null;
	private String filenameXml;
	private String contentTypeXml;

	@SkipValidation
	public HttpHeaders index() {

		museo = (Museo) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.MUSEOSEL);

		if (museo != null) {
			this.museo = museoNegocio.findById(this.museo.getIdMuseo());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.MUSEOSEL, museo);
			list = museo.getExposiciones();
		}
		if (idMuseoSel != null) {
			this.museo = museoNegocio.findById(idMuseoSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.MUSEOSEL, museo);
			list = museo.getExposiciones();
		}

		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		museo = (Museo) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.MUSEOSEL);

		if (museo != null) {
			idMuseoSel = museo.getIdMuseo();
		}

		museos = museoNegocio.findAll();
		subtemasExpo = new ArrayList<Subtema>();
		subtemasRegistrados = subtemaNegocio.findAll();

		return "editNew";
	}

	public void validateCreate() {

		museo = (Museo) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.MUSEOSEL);
		museo = museoNegocio.findById(museo.getIdMuseo());
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.MUSEOSEL, museo);
		subtemasRegistrados = subtemaNegocio.findAll();
		Subtema temita;
		subtemasExpo = new ArrayList<Subtema>();
		// System.out.println("En Create");
		for (Integer aux : idSubtemasSeleccionados) {
			temita = subtemaNegocio.findById(aux);
			// System.out.println("Temita "+temita.getNombre());
			subtemasExpo.add(temita);
		}

		if (subtemasExpo.size() <= 0) {
			addActionError("Debe seleccionar almenos un subtema.");
		}
		if (exposicionNegocio.existe(model)) {
			addActionError("Ya existe una exposición llamada "
					+ model.getNombre());
		}

		if (model.getTipo().equals("Temporal")) {
			if (model.getFhFin().isEmpty()) {
				addActionError("Introduzca la fecha de fin");
			}
			if (model.getFhInicio().isEmpty()) {
				addActionError("Introduzca la fecha de inicio");
			}

		} else {
			model.setFhFin(null);
			model.setFhInicio(null);
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre de la exposición."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción de la exposición.") })
	public HttpHeaders create() {

		// museo = (Museo) ActionContext.getContext().getSession()
		// .get(NombreObjetosSesion.MUSEOSEL);
		// Subtema temita;
		// subtemasExpo=new ArrayList<Subtema>();
		// // System.out.println("En Create");
		// for(Integer aux:idSubtemasSeleccionados){
		// temita=subtemaNegocio.findById(aux);
		// // System.out.println("Temita "+temita.getNombre());
		// subtemasExpo.add(temita);
		// }
		addActionMessage("La exposición "+ model.getNombre()+" ha sido agregada correctamente.");
		model.setSubtemas(subtemasExpo);
		model.setIdMuseo(museo.getIdMuseo());
		exposicionNegocio.save(model);
		byte[] bytesDat = null;
		byte[] bytesXml = null;
		
		if (fileDat != null && fileXml!=null) {
		if (fileDat != null) {
			bytesDat = new byte[(int) fileDat.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileDat);
				fileInputStream.read(bytesDat);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (fileXml != null) {
			bytesXml = new byte[(int) fileXml.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileXml);
				fileInputStream.read(bytesXml);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
			
			File dir=new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museo_" + model.getIdMuseo());
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirS = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds");
			if (!dirS.exists()) {
				dirS.mkdir();
			}
			File dirE = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds/Expo_" + model.getIdExposicion());
			if (!dirE.exists()) {
				dirE.mkdir();
			}			

			File archDat = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds/Expo_" + model.getIdExposicion() + "/" +model.getIdExposicion()+".dat" );
			File archXml = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds/Expo_" + model.getIdExposicion() + "/" +model.getIdExposicion()+".xml" );
			try {
				if (!archDat.exists()) {
					archDat.createNewFile();
				}
				if (!archXml.exists()) {
					archXml.createNewFile();
				}	
				FileCopyUtils.copy(bytesDat, archDat);				
				FileCopyUtils.copy(bytesXml, archXml);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
					
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdExposicion());
	}

	@SkipValidation
	public String edit() {
		List<Integer> idSubtemasEnExpo = new ArrayList<Integer>();
		subtemasRegistrados = new ArrayList<Subtema>();
		for (Subtema aux : model.getSubtemas()) {			
			idSubtemasEnExpo.add(aux.getIdSubtema());
		}
		for (Subtema aux : subtemaNegocio.findAll()) {
			if (!idSubtemasEnExpo.contains(aux.getIdSubtema())) {
				subtemasRegistrados.add(aux);
			}			
		}
		return "edit";
	}

	public void validateUpdate() {
		List<Integer> idTemasEnMuseo = new ArrayList<Integer>();
		subtemasRegistrados=new ArrayList<Subtema>();
		for(Subtema aux:model.getSubtemas()){
			idTemasEnMuseo.add(aux.getIdSubtema());
		}
		for(Subtema aux:subtemaNegocio.findAll()){
			if(!idTemasEnMuseo.contains(aux.getIdSubtema())){
				subtemasRegistrados.add(aux);
			}
		}
		Subtema temita;
		ExpoSubtema expoSubtema=new ExpoSubtema();		
		expoSubtema.setIdExposicion(model.getIdExposicion());
		subtemasExpo=new ArrayList<Subtema>();

		for(Subtema aux:model.getSubtemas()){					
			expoSubtema.setExposubtemaId(new ExpoSubtemaId(model.getIdExposicion(),aux.getIdSubtema()));
			expoSubtemaNegocio.delete(expoSubtema);
		}

		for(Integer aux:idSubtemasSeleccionados){
			temita=subtemaNegocio.findById(aux);
			System.out.println("Temita "+temita.getNombre());	
			subtemasExpo.add(temita);			
		}


		if (subtemasExpo.size() <= 0) {
			addActionError("Debe seleccionar almenos un subtema.");
		}
		if (exposicionNegocio.existe(model)) {
			addActionError("Ya existe una exposición llamada "
					+ model.getNombre());
		}

		if (model.getTipo().equals("Temporal")) {
			if (model.getFhFin().isEmpty()) {
				addActionError("Introduzca la fecha de fin");
			}
			if (model.getFhInicio().isEmpty()) {
				addActionError("Introduzca la fecha de inicio");
			}

		} else {
			model.setFhFin(null);
			model.setFhInicio(null);
		}

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduce el nombre de la exposición."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduce la descripcion de la exposición.") })
	public String update() {
		// exposicionNegocio.save(model);
		if (idSubtemasSeleccionados.size() <= 0) {
			addActionError("Debe seleccionar almenos un subtema.");
		}
		Subtema temita;
		ExpoSubtema expoSubtema = new ExpoSubtema();
		// museoTemaNegocio=new MuseoTemaNegocio();
		expoSubtema.setIdExposicion(model.getIdExposicion());
		subtemasExpo = new ArrayList<Subtema>();
		// System.out.println("MuseoTema id museo "+model.getIdExposicion());
		// System.out.println("en update");
		for (Subtema aux : model.getSubtemas()) {
			// System.out.println("borrando "+aux.getNombre() + "idM "+
			// model.getIdExposicion() + "idT "+ aux.getIdSubtema());
			expoSubtema.setExposubtemaId(new ExpoSubtemaId(model
					.getIdExposicion(), aux.getIdSubtema()));
			expoSubtemaNegocio.delete(expoSubtema);
		}

		// for(Integer aux:idSubtemasSeleccionados){
		// temita=subtemaNegocio.findById(aux);
		// //System.out.println("Temita seleccionado "+temita.getNombre());
		// }
		for (Integer aux : idSubtemasSeleccionados) {
			temita = subtemaNegocio.findById(aux);
			// System.out.println("Temita "+temita.getNombre());
			subtemasExpo.add(temita);
		}
		addActionMessage("La exposición "+ model.getNombre()+" ha sido modificada correctamente.");
		model.setSubtemas(subtemasExpo);
		exposicionNegocio.save(model);
		
		
		byte[] bytesDat = null;
		byte[] bytesXml = null;
		if (fileDat != null && fileXml!=null) {
		if (fileDat != null) {
			bytesDat = new byte[(int) fileDat.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileDat);
				fileInputStream.read(bytesDat);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (fileXml != null) {
			bytesXml = new byte[(int) fileXml.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileXml);
				fileInputStream.read(bytesXml);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
			
			File dir=new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museo_" + model.getIdMuseo());
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirS = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds");
			if (!dirS.exists()) {
				dirS.mkdir();
			}
			File dirE = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds/Expo_" + model.getIdExposicion());
			if (!dirE.exists()) {
				dirE.mkdir();
			}			
			
			
			File archDat = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds/Expo_" + model.getIdExposicion() + "/" +model.getIdExposicion()+".dat" );			
			File archXml = new File("Repo/Museo_" + model.getIdMuseo()
					+ "/sounds/Expo_" + model.getIdExposicion() + "/" +model.getIdExposicion()+".xml" );
			try {
				if (fileDat != null) {
				if (!archDat.exists()) {
					archDat.createNewFile();
				}
				FileCopyUtils.copy(bytesDat, archDat);
				}
				if (fileXml != null) {
				if (!archXml.exists()) {
					archXml.createNewFile();
				}	
				FileCopyUtils.copy(bytesXml, archXml);
				}								
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {

	}

	@SkipValidation
	public String destroy() {
		addActionMessage("La exposición " + model.getNombre()
				+ " ha sido elmiminada");
		exposicionNegocio.delete(model);
		return "success";
	}

	// /////////////////////////////////////////////////////////////////////////////////////////
	public Exposicion getModel() {
		if (model == null) {
			model = new Exposicion();
		}
		return model;
	}

	public void setModel(Exposicion model) {
		this.model = model;
	}

	public ExposicionNegocio getExposicionNegocio() {
		return exposicionNegocio;
	}

	public void setExposicionNegocio(ExposicionNegocio exposicionNegocio) {
		this.exposicionNegocio = exposicionNegocio;
	}

	public List<Exposicion> getList() {
		return list;
	}

	public void setList(List<Exposicion> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = exposicionNegocio.findById(idSel);
		}
	}

	public List<Museo> getMuseos() {
		return museos;
	}

	public void setMuseos(List<Museo> museos) {
		this.museos = museos;
	}

	public List<Subtema> getSubtemas() {
		return subtemas;
	}

	public void setSubtemas(List<Subtema> subtemas) {
		this.subtemas = subtemas;
	}

	public Subtema getSubtema() {
		return subtema;
	}

	public void setSubtema(Subtema subtema) {
		this.subtema = subtema;
	}

	public MuseoNegocio getMuseoNegocio() {
		return museoNegocio;
	}

	public void setMuseoNegocio(MuseoNegocio museoNegocio) {
		this.museoNegocio = museoNegocio;
	}

	public SubtemaNegocio getSubtemaNegocio() {
		return subtemaNegocio;
	}

	public void setSubtemaNegocio(SubtemaNegocio subtemaNegocio) {
		this.subtemaNegocio = subtemaNegocio;
	}

	public List<Subtema> getSubtemasRegistrados() {
		return subtemasRegistrados;
	}

	public void setSubtemasRegistrados(List<Subtema> subtemasRegistrados) {
		this.subtemasRegistrados = subtemasRegistrados;
	}

	public List<Subtema> getSubtemasExpo() {
		return subtemasExpo;
	}

	public void setSubtemasExpo(List<Subtema> subtemasExpo) {
		this.subtemasExpo = subtemasExpo;
	}

	public List<Integer> getIdSubtemasSeleccionados() {
		return idSubtemasSeleccionados;
	}

	public void setIdSubtemasSeleccionados(List<Integer> idSubtemasSeleccionados) {
		this.idSubtemasSeleccionados = idSubtemasSeleccionados;
	}

	public List<Integer> getIdSubtemasDisponibles() {
		return idSubtemasDisponibles;
	}

	public void setIdSubtemasDisponibles(List<Integer> idSubtemasDisponibles) {
		this.idSubtemasDisponibles = idSubtemasDisponibles;
	}

	public ExpoSubtemaNegocio getExpoSubtemaNegocio() {
		return expoSubtemaNegocio;
	}

	public void setExpoSubtemaNegocio(ExpoSubtemaNegocio expoSubtemaNegocio) {
		this.expoSubtemaNegocio = expoSubtemaNegocio;
	}

	public Integer getIdMuseoSel() {
		return idMuseoSel;
	}

	public void setIdMuseoSel(Integer idMuseoSel) {
		this.idMuseoSel = idMuseoSel;
	}

	public Museo getMuseo() {
		return museo;
	}

	public void setMuseo(Museo museo) {
		this.museo = museo;
	}

	public List<String> getType() {
		type.add("Permanente");
		type.add("Temporal");
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public List<String> getEdo() {
		edo.add("Activa");
		edo.add("Inactiva");
		return edo;
	}

	public void setEdo(List<String> edo) {
		this.edo = edo;
	}
	
	/**
	 ***********************************************************************
	 * 
	 */
		public File getFileDat() {
			return fileDat;
		}

		public void setFileDat(File file) {
			this.fileDat = file;
		}

		public String getFilenameDat() {
			return filenameDat;
		}

		public void setFilenameDat(String filename) {
			this.filenameDat = filename;
		}

		public String getContentTypeDat() {
			return contentTypeDat;
		}

		public void setContentTypeDat(String contentType) {
			this.contentTypeDat = contentType;
		}

		public void setArchivoDat(File file) {
			this.fileDat = file;
		}

		public void setArchivoDatContentType(String contentType) {
			this.contentTypeDat = contentType;
		}

		public void setArchivoDatFileName(String filename) {
			this.filenameDat = filename;
		}
		
		/**
		 ***********************************************************************
		 * 
		 */
			public File getFileXml() {
				return fileXml;
			}

			public void setFileXml(File file) {
				this.fileXml = file;
			}

			public String getFilenameXml() {
				return filenameXml;
			}

			public void setFilenameXml(String filename) {
				this.filenameXml = filename;
			}

			public String getContentTypeXml() {
				return contentTypeXml;
			}

			public void setContentTypeXml(String contentType) {
				this.contentTypeXml = contentType;
			}

			public void setArchivoXml(File file) {
				this.fileXml = file;
			}

			public void setArchivoXmlContentType(String contentType) {
				this.contentTypeXml = contentType;
			}

			public void setArchivoXmlFileName(String filename) {
				this.filenameXml = filename;
			}

}
