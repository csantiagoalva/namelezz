package mx.ipn.escom.ars.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.modelo.MuseoTema;
import mx.ipn.escom.ars.modelo.MuseoTemaId;
import mx.ipn.escom.ars.modelo.Tema;
import mx.ipn.escom.ars.negocio.MuseoNegocio;
import mx.ipn.escom.ars.negocio.MuseoTemaNegocio;
import mx.ipn.escom.ars.negocio.TemaNegocio;
import mx.ipn.escom.ars.util.Marco;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.omg.CORBA.Request;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.sun.tools.javac.util.DefaultFileManager.Archive;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-museo" }) })
public class CatalogoMuseoController extends ActionSupport implements
		ModelDriven<Museo> {

	private static final long serialVersionUID = 558107555367329498L;

	private Museo model;
	private MuseoNegocio museoNegocio;
	private TemaNegocio temaNegocio;
	private MuseoTemaNegocio museoTemaNegocio;
	private List<Museo> list;
	private List<Tema> temasRegistrados;
	private List<Tema> temasMuseo;
	private Integer idSel;
	private List<Integer> idTemasSeleccionados = new ArrayList<Integer>();
	private List<Integer> idTemasDisponibles = new ArrayList<Integer>();
	private List<String> edo = new ArrayList<String>();
	
	private File fileImg = null;
	private String filenameImg;
	private String contentTypeImg;

	@SkipValidation
	public HttpHeaders index() {						
		
		list = museoNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		// temasMuseo=new ArrayList<Tema>();
		temasRegistrados = temaNegocio.findAll();
		return "editNew";
	}

	public void validateCreate() {
		temasRegistrados = temaNegocio.findAll();
		Tema temita;
		temasMuseo = new ArrayList<Tema>();

		for (Integer aux : idTemasSeleccionados) {
			temita = temaNegocio.findById(aux);			
			temasMuseo.add(temita);
		}
		if (temasMuseo.size() <= 0) {
			addActionError("Seleccione almenos un Tema.");
		}
		if (museoNegocio.existe(model)) {
			addActionError("Ya existe un museo llamado " + model.getNombre());
		}
		if (museoNegocio.ubicacionRepetida(model) == true) {
			addActionError("No se puede ubicar el museo en esta dirección");
		}			

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del museo."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del museo."),
			@RequiredStringValidator(fieldName = "model.calle", type = ValidatorType.FIELD, key = "Introduzca el nombre de la calle."),
			@RequiredStringValidator(fieldName = "model.numero", type = ValidatorType.FIELD, key = "Introduzca el número."),
			@RequiredStringValidator(fieldName = "model.colonia", type = ValidatorType.FIELD, key = "Introduzca el nombre de la colonia."),
			@RequiredStringValidator(fieldName = "model.delegacion", type = ValidatorType.FIELD, key = "Introduzca el nombre de la delegación."),
			@RequiredStringValidator(fieldName = "model.estado", type = ValidatorType.FIELD, key = "Introduzca el estado."),
			@RequiredStringValidator(fieldName = "model.contacto", type = ValidatorType.FIELD, key = "Introduzca algún contacto.") }, requiredFields = { @RequiredFieldValidator(fieldName = "model.codigo", key = "Introduzca el cÃ³digo postal", type = ValidatorType.SIMPLE) }
	// ,expressions = {@ExpressionValidator(expression =
	// "not temasMuseo.isEmpty", key = "Seleccione almenos un Tema.")}
	)
	public HttpHeaders create() {

		addActionMessage(model.getNombre()+" ha sido agregado correctamente.");
		model.setTemas(temasMuseo);
		model = museoNegocio.save(model);
		
		byte[] bytes = null;

		if (fileImg != null) {
			bytes = new byte[(int) fileImg.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileImg);
				fileInputStream.read(bytes);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			File dir=new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museos");
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirI = new File("Repo/Museos/img");
			if (!dirI.exists()) {
				dirI.mkdir();
			}			
			
			String ext=filenameImg.substring(filenameImg.length()-4);									
						
			File arch = new File("Repo/Museos/img/" +model.getIdMuseo()+ext );
			try {
				arch.createNewFile();
				FileCopyUtils.copy(bytes, arch);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Marco.transformar(arch);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdMuseo());
	}

	@SkipValidation
	public String edit() {
		List<Integer> idTemasEnMuseo = new ArrayList<Integer>();
		temasRegistrados = new ArrayList<Tema>();
		for (Tema aux : model.getTemas()) {
			idTemasEnMuseo.add(aux.getIdTema());
		}
		for (Tema aux : temaNegocio.findAll()) {
			if (!idTemasEnMuseo.contains(aux.getIdTema())) {
				temasRegistrados.add(aux);
			}
		}

		return "edit";
	}

	public void validateUpdate() {
		List<Integer> idTemasEnMuseo = new ArrayList<Integer>();
		temasRegistrados = new ArrayList<Tema>();
		for (Tema aux : model.getTemas()) {
			idTemasEnMuseo.add(aux.getIdTema());
		}
		for (Tema aux : temaNegocio.findAll()) {
			if (!idTemasEnMuseo.contains(aux.getIdTema())) {
				temasRegistrados.add(aux);
			}
		}
		Tema temita;
		MuseoTema museoTema = new MuseoTema();
		museoTema.setIdMuseo(model.getIdMuseo());
		temasMuseo = new ArrayList<Tema>();

		for (Tema aux : model.getTemas()) {
			museoTema.setMuseoTemaId(new MuseoTemaId(model.getIdMuseo(), aux
					.getIdTema()));
			museoTemaNegocio.delete(museoTema);
		}

		for (Integer aux : idTemasSeleccionados) {
			temita = temaNegocio.findById(aux);			
		}
		for (Integer aux : idTemasSeleccionados) {
			temita = temaNegocio.findById(aux);
			System.out.println("Temita " + temita.getNombre());
			temasMuseo.add(temita);
		}

		if (temasMuseo.size() <= 0) {
			addActionError("Seleccione almenos un Tema.");
		}
		if (museoNegocio.existe(model)) {
			addActionError("Ya existe un museo llamado " + model.getNombre());
		}
		if (museoNegocio.ubicacionRepetida(model) == true) {
			addActionError("No se puede ubicar el museo en esta dirección");
		}

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del museo."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del museo."),
			@RequiredStringValidator(fieldName = "model.calle", type = ValidatorType.FIELD, key = "Introduzca el nombre de la calle."),
			@RequiredStringValidator(fieldName = "model.numero", type = ValidatorType.FIELD, key = "Introduzca el número."),
			@RequiredStringValidator(fieldName = "model.colonia", type = ValidatorType.FIELD, key = "Introduzca el nombre de la colonia."),
			@RequiredStringValidator(fieldName = "model.delegacion", type = ValidatorType.FIELD, key = "Introduzca el nombre de la delegación."),
			@RequiredStringValidator(fieldName = "model.estado", type = ValidatorType.FIELD, key = "Introduzca el estado."),
			@RequiredStringValidator(fieldName = "model.contacto", type = ValidatorType.FIELD, key = "Introduzca algún contacto.") }, requiredFields = { @RequiredFieldValidator(fieldName = "model.codigo", key = "Introduzca el cÃ³digo postal", type = ValidatorType.SIMPLE) })
	public String update() {

		Tema temita;
		MuseoTema museoTema = new MuseoTema();
		museoTema.setIdMuseo(model.getIdMuseo());
		temasMuseo = new ArrayList<Tema>();

		for (Tema aux : model.getTemas()) {
			museoTema.setMuseoTemaId(new MuseoTemaId(model.getIdMuseo(), aux
					.getIdTema()));
			museoTemaNegocio.delete(museoTema);
		}

		for (Integer aux : idTemasSeleccionados) {
			temita = temaNegocio.findById(aux);
			System.out.println("Temita seleccionado " + temita.getNombre());
		}
		for (Integer aux : idTemasSeleccionados) {
			temita = temaNegocio.findById(aux);
			System.out.println("Temita " + temita.getNombre());
			temasMuseo.add(temita);
		}

		museoNegocio.cambiaEstado(model);
		addActionMessage(model.getNombre()+" ha sido modificado correctamente.");
		model.setTemas(temasMuseo);
		museoNegocio.save(model);
		
		byte[] bytes = null;

		if (fileImg != null) {
			bytes = new byte[(int) fileImg.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileImg);
				fileInputStream.read(bytes);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			File dir=new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museos");
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirI = new File("Repo/Museos/img");
			if (!dirI.exists()) {
				dirI.mkdir();
			}			
			String ext=filenameImg.substring(filenameImg.length()-4);									
			
			File arch = new File("Repo/Museos/img/" +model.getIdMuseo()+ext );
			try {
				arch.createNewFile();
				FileCopyUtils.copy(bytes, arch);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Marco.transformar(arch);
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
		addActionMessage(model.getNombre()
				+ " ha sido elmiminado");
		museoNegocio.delete(model);
		return "success";
	}

	// public void validate() {
	// temasMuseo=new ArrayList<Tema>();
	// temasRegistrados=temaNegocio.findAll();
	// }

	// ///////////////////////////////////////////////////////////////////////////////////////////////////
	public Museo getModel() {
		if (model == null)
			model = new Museo();
		return model;
	}

	public void setModel(Museo model) {

		this.model = model;
	}

	public MuseoNegocio getMuseoNegocio() {
		return museoNegocio;
	}

	public void setMuseoNegocio(MuseoNegocio museoNegocio) {
		this.museoNegocio = museoNegocio;
	}

	public List<Museo> getList() {
		return list;
	}

	public void setList(List<Museo> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = museoNegocio.findById(idSel);
		}
	}

	public List<Tema> getTemasRegistrados() {
		return temasRegistrados;
	}

	public void setTemasRegistrados(List<Tema> temas) {
		this.temasRegistrados = temas;
	}

	public TemaNegocio getTemaNegocio() {
		return temaNegocio;
	}

	public void setTemaNegocio(TemaNegocio temaNegocio) {
		this.temaNegocio = temaNegocio;
	}

	public List<Tema> getTemasMuseo() {
		return temasMuseo;
	}

	public void setTemasMuseo(List<Tema> temasMuseo) {
		this.temasMuseo = temasMuseo;
	}

	public List<Integer> getIdTemasSeleccionados() {
		return idTemasSeleccionados;
	}

	public void setIdTemasSeleccionados(List<Integer> idTemasSeleccionados) {
		this.idTemasSeleccionados = idTemasSeleccionados;
	}

	public MuseoTemaNegocio getMuseoTemaNegocio() {
		return museoTemaNegocio;
	}

	public void setMuseoTemaNegocio(MuseoTemaNegocio museoTemaNegocio) {
		this.museoTemaNegocio = museoTemaNegocio;
	}

	public List<Integer> getIdTemasDisponibles() {
		return idTemasDisponibles;
	}

	public void setIdTemasDisponibles(List<Integer> idTemasDisponibles) {
		this.idTemasDisponibles = idTemasDisponibles;
	}

	public List<String> getEdo() {
		edo.add("Activo");
		edo.add("Inactivo");
		return edo;
	}

	public void setEdo(List<String> edo) {
		this.edo = edo;
	}
	/**
	 ***********************************************************************
	 * 
	 */
		public File getFileImg() {
			return fileImg;
		}

		public void setFileImg(File file) {
			this.fileImg = file;
		}

		public String getFilenameImg() {
			return filenameImg;
		}

		public void setFilenameImg(String filename) {
			this.filenameImg = filename;
		}

		public String getContentTypeImg() {
			return contentTypeImg;
		}

		public void setContentTypeImg(String contentType) {
			this.contentTypeImg = contentType;
		}

		public void setArchivoImagen(File file) {
			this.fileImg = file;
		}

		public void setArchivoImagenContentType(String contentType) {
			this.contentTypeImg = contentType;
		}

		public void setArchivoImagenFileName(String filename) {
			this.filenameImg = filename;
		}
}
