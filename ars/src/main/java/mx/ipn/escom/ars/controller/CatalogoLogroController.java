package mx.ipn.escom.ars.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.ars.modelo.Elemento;
import mx.ipn.escom.ars.modelo.ElementoLogro;
import mx.ipn.escom.ars.modelo.ElementoLogroId;
import mx.ipn.escom.ars.modelo.Logro;
import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.modelo.Recompensa;
import mx.ipn.escom.ars.negocio.ElementoLogroNegocio;
import mx.ipn.escom.ars.negocio.ElementoNegocio;
import mx.ipn.escom.ars.negocio.LogroNegocio;
import mx.ipn.escom.ars.negocio.MuseoNegocio;
import mx.ipn.escom.ars.negocio.RecompensaNegocio;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-logro?idMuseoSel=%{idMuseoSel}" }) })
public class CatalogoLogroController extends ActionSupport implements
		ModelDriven<Logro> {

	private static final long serialVersionUID = 558107555367329498L;

	private Logro model;
	private LogroNegocio logroNegocio;
	private ElementoNegocio elementoNegocio;
	private ElementoLogroNegocio elementoLogroNegocio;
	private List<Logro> list;
	private List<Elemento> elementosRegistrados;
	private List<Elemento> elementosLogro;
	private Integer idSel = null;
	private Integer idMuseoSel;
	private Recompensa recompensa;

	private Museo museo;
	private MuseoNegocio museoNegocio;
	private RecompensaNegocio recompensaNegocio;

	private List<Integer> idElementosSeleccionados = new ArrayList<Integer>();
	private List<Integer> idElementosDisponibles = new ArrayList<Integer>();

	private File file = null;
	private String filename;
	private String contentType;

	private File fileT = null;
	private String filenameT;
	private String contentTypeT;

	@SkipValidation
	public HttpHeaders index() {

		list = logroNegocio.findByMuseo(museo);

		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		elementosRegistrados = elementoNegocio.findByMuseo(museo);
		return "editNew";
	}

	public void validateCreate() {
		elementosRegistrados = elementoNegocio.findAll();
		Elemento temita;
		elementosLogro = new ArrayList<Elemento>();

		for (Integer aux : idElementosSeleccionados) {
			temita = elementoNegocio.findById(aux);
			elementosLogro.add(temita);
		}
		if (elementosLogro.size() <= 0) {
			addActionError("Seleccione almenos un Elemento.");
		}

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del logro."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del logro.") })
	public HttpHeaders create() {

		addActionMessage(model.getNombre() + " ha sido agregado correctamente.");
		model.setElementos(elementosLogro);
		model = logroNegocio.save(model);

		byte[] bytes = null;
		byte[] bytes2 = null;

		if (file != null && fileT != null) {
			
			bytes = new byte[(int) file.length()];
			bytes2 = new byte[(int) fileT.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytes);
				FileInputStream fileInputStream2 = new FileInputStream(fileT);
				fileInputStream2.read(bytes2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			File dir = new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museos");
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirR = new File("Repo/Museos/Recursos");
			if (!dirR.exists()) {
				dirR.mkdir();
			}
			File dirI = new File("Repo/Museos/Recursos/Recompensas");
			if (!dirI.exists()) {
				dirI.mkdir();
			}

			File arch = new File("Repo/Museos/Recursos/Recompensas/" + filename);
			File arch2 = new File("Repo/Museos/Recursos/Recompensas/" + filenameT);
			try {
				arch.createNewFile();
				arch2.createNewFile();
				FileCopyUtils.copy(bytes, arch);
				FileCopyUtils.copy(bytes2, arch2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Recompensa recompensa=new Recompensa();
			recompensa.setNombre(filename);
			recompensa.setUrl(arch.getAbsolutePath());
			recompensa.setUrlText(arch2.getAbsolutePath());
			recompensa.setIdLogro(model.getIdLogro());
			recompensaNegocio.save(recompensa);
		}
		

		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdLogro());
	}

	@SkipValidation
	public String edit() {
		List<Integer> idElementosEnLogro = new ArrayList<Integer>();
		elementosRegistrados = new ArrayList<Elemento>();
		for (Elemento aux : model.getElementos()) {
			idElementosEnLogro.add(aux.getIdElemento());
		}
		for (Elemento aux : elementoNegocio.findByMuseo(museo)) {
			if (!idElementosEnLogro.contains(aux.getIdElemento())) {
				elementosRegistrados.add(aux);
			}
		}

		return "edit";
	}

	public void validateUpdate() {
		List<Integer> idElementosEnLogro = new ArrayList<Integer>();
		elementosRegistrados = new ArrayList<Elemento>();
		for (Elemento aux : model.getElementos()) {
			idElementosEnLogro.add(aux.getIdElemento());
		}
		for (Elemento aux : elementoNegocio.findAll()) {
			if (!idElementosEnLogro.contains(aux.getIdElemento())) {
				elementosRegistrados.add(aux);
			}
		}
		Elemento temita;
		ElementoLogro elementoLogro = new ElementoLogro();
		elementoLogro.setIdLogro(model.getIdLogro());
		elementosLogro = new ArrayList<Elemento>();

		for (Elemento aux : model.getElementos()) {
			elementoLogro.setElementoLogroId(new ElementoLogroId(model
					.getIdLogro(), aux.getIdElemento()));
			elementoLogroNegocio.delete(elementoLogro);
		}

		for (Integer aux : idElementosSeleccionados) {
			temita = elementoNegocio.findById(aux);
		}
		for (Integer aux : idElementosSeleccionados) {
			temita = elementoNegocio.findById(aux);
			elementosLogro.add(temita);
		}

		if (elementosLogro.size() <= 0) {
			addActionError("Seleccione almenos un Elemento.");
		}

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del logro."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del logro.") })
	public String update() {

		Elemento temita;
		ElementoLogro elementoLogro = new ElementoLogro();
		elementoLogro.setIdLogro(model.getIdLogro());
		elementosLogro = new ArrayList<Elemento>();

		for (Elemento aux : model.getElementos()) {
			elementoLogro.setElementoLogroId(new ElementoLogroId(model
					.getIdLogro(), aux.getIdElemento()));
			elementoLogroNegocio.delete(elementoLogro);
		}

		for (Integer aux : idElementosSeleccionados) {
			temita = elementoNegocio.findById(aux);
		}
		for (Integer aux : idElementosSeleccionados) {
			temita = elementoNegocio.findById(aux);
			elementosLogro.add(temita);
		}

		addActionMessage(model.getNombre()
				+ " ha sido modificado correctamente.");
		model.setElementos(elementosLogro);
		

		byte[] bytes = null;
		byte[] bytes2 = null;

		if (file != null && fileT != null) {

			if (!model.getRecompensas().isEmpty()) {
				File del = new File(recompensa.getUrl());
				del.delete();
				del = new File(recompensa.getUrlText());
				del.delete();
			}

			bytes = new byte[(int) file.length()];
			bytes2 = new byte[(int) fileT.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytes);
				FileInputStream fileInputStream2 = new FileInputStream(fileT);
				fileInputStream2.read(bytes2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			File dir = new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museos");
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirR = new File("Repo/Museos/Recursos");
			if (!dirR.exists()) {
				dirR.mkdir();
			}
			File dirI = new File("Repo/Museos/Recursos/Recompensas");
			if (!dirI.exists()) {
				dirI.mkdir();
			}

			File arch = new File("Repo/Museos/Recursos/Recompensas/" + filename);
			File arch2 = new File("Repo/Museos/Recursos/Recompensas/" + filenameT);
			try {
				arch.createNewFile();
				arch2.createNewFile();
				FileCopyUtils.copy(bytes, arch);
				FileCopyUtils.copy(bytes2, arch2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			recompensa.setNombre(filename);
			recompensa.setUrl(arch.getAbsolutePath());
			recompensa.setUrlText(arch2.getAbsolutePath());
			recompensa.setIdLogro(model.getIdLogro());
			recompensaNegocio.save(recompensa);
		}
		logroNegocio.save(model);

		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		elementosLogro = model.getElementos();

		return "deleteConfirm";
	}

	public void validateDestroy() {

	}

	@SkipValidation
	public String destroy() {
		addActionMessage(model.getNombre() + " ha sido elmiminado");

		if (!model.getRecompensas().isEmpty()) {
			File del = new File(recompensa.getUrl());
			del.delete();
			del = new File(recompensa.getUrlText());
			del.delete();
		}
		
		logroNegocio.delete(model);

		return "success";
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////
	public Logro getModel() {
		if (model == null) {
			model = new Logro();
		}

		return model;
	}

	public void setModel(Logro model) {

		this.model = model;
	}

	public LogroNegocio getLogroNegocio() {
		return logroNegocio;
	}

	public void setLogroNegocio(LogroNegocio logroNegocio) {
		this.logroNegocio = logroNegocio;
	}

	public List<Logro> getList() {
		return list;
	}

	public void setList(List<Logro> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = logroNegocio.findById(idSel);
//			if (idSel != null) {
//				Recompensa r = new Recompensa();
//				r.setIdLogro(idSel);
//
//				if (!recompensaNegocio.findByExample(r).isEmpty()) {
//					recompensa = recompensaNegocio.findByExample(r).get(0);
//				} else {
//					recompensa = new Recompensa();
//				}
//
//			}
			if(!model.getRecompensas().isEmpty()){
				recompensa=model.getRecompensas().get(0);
				System.out.println("existe");
			}else{
				recompensa=new Recompensa();
				System.out.println("nuevo");
			}
			
		}

	}

	public List<Elemento> getElementosRegistrados() {
		return elementosRegistrados;
	}

	public void setElementosRegistrados(List<Elemento> elementos) {
		this.elementosRegistrados = elementos;
	}

	public ElementoNegocio getElementoNegocio() {
		return elementoNegocio;
	}

	public void setElementoNegocio(ElementoNegocio elementoNegocio) {
		this.elementoNegocio = elementoNegocio;
	}

	public List<Elemento> getElementosLogro() {
		return elementosLogro;
	}

	public void setElementosLogro(List<Elemento> elementosLogro) {
		this.elementosLogro = elementosLogro;
	}

	public List<Integer> getIdElementosSeleccionados() {
		return idElementosSeleccionados;
	}

	public void setIdElementosSeleccionados(
			List<Integer> idElementosSeleccionados) {
		this.idElementosSeleccionados = idElementosSeleccionados;
	}

	public ElementoLogroNegocio getElementoLogroNegocio() {
		return elementoLogroNegocio;
	}

	public void setElementoLogroNegocio(
			ElementoLogroNegocio elementoLogroNegocio) {
		this.elementoLogroNegocio = elementoLogroNegocio;
	}

	public List<Integer> getIdElementosDisponibles() {
		return idElementosDisponibles;
	}

	public void setIdElementosDisponibles(List<Integer> idElementosDisponibles) {
		this.idElementosDisponibles = idElementosDisponibles;
	}

	/**
	 ***********************************************************************
	 * 
	 */
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setArchivo(File file) {
		this.file = file;
	}

	public void setArchivoContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setArchivoFileName(String filename) {
		this.filename = filename;
	}

	public Integer getIdMuseoSel() {
		return idMuseoSel;
	}

	public void setIdMuseoSel(Integer idMuseoSel) {
		if (idMuseoSel != null) {
			museo = museoNegocio.findById(idMuseoSel);
		}
		this.idMuseoSel = idMuseoSel;
	}

	public MuseoNegocio getMuseoNegocio() {
		return museoNegocio;
	}

	public void setMuseoNegocio(MuseoNegocio museoNegocio) {
		this.museoNegocio = museoNegocio;
	}

	public Recompensa getRecompensa() {
		if (recompensa == null) {
			recompensa = new Recompensa();
		}
		return recompensa;
	}

	public void setRecompensa(Recompensa recompensa) {
		this.recompensa = recompensa;
	}

	public File getFileT() {
		return fileT;
	}

	public void setFileT(File fileT) {
		this.fileT = fileT;
	}

	public String getFilenameT() {
		return filenameT;
	}

	public void setFilenameT(String filenameT) {
		this.filenameT = filenameT;
	}

	public String getContentTypeT() {
		return contentTypeT;
	}

	public void setContentTypeT(String contentTypeT) {
		this.contentTypeT = contentTypeT;
	}

	public void setTextura(File file) {
		this.fileT = file;
	}

	public void setTexturaContentType(String contentType) {
		this.contentTypeT = contentType;
	}

	public void setTexturaFileName(String filename) {
		this.filenameT = filename;
	}

	public RecompensaNegocio getRecompensaNegocio() {
		return recompensaNegocio;
	}

	public void setRecompensaNegocio(RecompensaNegocio recompensaNegocio) {
		this.recompensaNegocio = recompensaNegocio;
	}

}
