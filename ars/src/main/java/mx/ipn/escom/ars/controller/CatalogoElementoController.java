package mx.ipn.escom.ars.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.ars.modelo.Elemento;
import mx.ipn.escom.ars.modelo.Exposicion;
import mx.ipn.escom.ars.modelo.Recurso;
import mx.ipn.escom.ars.negocio.ElementoNegocio;
import mx.ipn.escom.ars.negocio.ExposicionNegocio;
import mx.ipn.escom.ars.negocio.RecursoNegocio;
import mx.ipn.escom.ars.util.NombreObjetosSesion;
import mx.ipn.escom.ars.util.Marco;

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
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-elemento?idExpoSel=%{idExpoSel}" }),
		@Result(name = "input", location = "catalogo-elemento/index-deleteConfirm.jsp"),
		@Result(name = "show", type = "redirectAction", params = {
				"actionName", "catalogo-elemento/new?idExpoSel=%{idExpoSel}" }) })
public class CatalogoElementoController extends ActionSupport implements
		ModelDriven<Elemento> {

	private static final long serialVersionUID = -7107264188963726964L;
	private ElementoNegocio elementoNegocio;
	private Elemento model = null;
	private Integer idSel = null;
	private List<Elemento> list = null;
	private List<Recurso> listRecurso = null;
	private Integer idExpoSel;
	private Integer idMuseoSel;

	private ExposicionNegocio exposicionNegocio;
	private Exposicion exposicion;
	private RecursoNegocio recursoNegocio;
	private Recurso recurso;
	
	private Recurso sonido;
	private Recurso imagen;

	private List<Recurso> recursosTemporales = null;
	private List<Integer> porEliminar;
	
	private File fileSnd = null;
	private String filenameSnd;
	private String contentTypeSnd;
	
	private File fileImg = null;
	private String filenameImg;
	private String contentTypeImg;

	@SkipValidation
	public HttpHeaders index() {
		list = exposicion.getElementos();

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.RECURSOSTEMPORALES, null);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.ACCION, null);
		
		porEliminar=new ArrayList<Integer>();
		ActionContext.getContext().getSession().put(NombreObjetosSesion.ELIMINAR, porEliminar);
		
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.RECURSO, "Repo/Museo_"+exposicion.getIdMuseo()+"/images/Expo_"+exposicion.getIdExposicion()+"/ImgExpo"+exposicion.getIdExposicion()+".zip");
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.CARPETA, "Repo/Museo_"+exposicion.getIdMuseo()+"/images/Expo_"+exposicion.getIdExposicion()+"/img");
		
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.ACCION, "crear");
		recursosTemporales = new ArrayList<Recurso>();

		if (ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.RECURSOSTEMPORALES) != null) {
			recursosTemporales = (List<Recurso>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.RECURSOSTEMPORALES);
		}

		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.RECURSOSTEMPORALES, recursosTemporales);

		return "editNew";
	}

	public void validateCreate() {

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del Elemento"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del Elemento") })
	
	public HttpHeaders create() {
		addActionMessage("El elemento "+ model.getNombre()+" ha sido agregado correctamente.");
		model.setIdExposicion(idExpoSel);		
		elementoNegocio.save(model);		
		
		sonido=new Recurso();
		sonido.setIdElemento(model.getIdElemento());
		byte[] bytes = null;

		if (fileSnd != null) {
			bytes = new byte[(int) fileSnd.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileSnd);
				fileInputStream.read(bytes);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
						
			sonido.setNombreArchivoFisico(filenameSnd);
			sonido.setArchivo(bytes);			
			
			File dir=new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museo_" + exposicion.getIdMuseo());
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirS = new File("Repo/Museo_" + exposicion.getIdMuseo()
					+ "/sounds");
			if (!dirS.exists()) {
				dirS.mkdir();
			}
			File dirE = new File("Repo/Museo_" + exposicion.getIdMuseo()
					+ "/sounds/Expo_" + idExpoSel);
			if (!dirE.exists()) {
				dirE.mkdir();
			}			

			File archDef = new File("Repo/Museo_" + exposicion.getIdMuseo()
					+ "/sounds/Expo_" + idExpoSel + "/" + sonido.getIdElemento()+".mp3");
			try {
				archDef.createNewFile();
				FileCopyUtils.copy(sonido.getArchivo(), archDef);
			} catch (IOException e) {
				e.printStackTrace();
			}

			sonido.setUrl(archDef.getAbsolutePath());
			recursoNegocio.save(sonido);
		}	
			imagen=new Recurso();
			imagen.setIdElemento(model.getIdElemento());		
			bytes = null;

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
			
										
				imagen.setNombreArchivoFisico(filenameImg);
				imagen.setArchivo(bytes);			
								
				File dirI = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images");
				if (!dirI.exists()) {
					dirI.mkdir();
				}
				File dirEx = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images/Expo_" + idExpoSel);
				if (!dirEx.exists()) {
					dirEx.mkdir();
				}
				File dirIm = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images/Expo_" + idExpoSel+"/img");
				if (!dirIm.exists()) {
					dirIm.mkdir();
				}

				String ext=imagen.getNombreArchivoFisico().substring(imagen.getNombreArchivoFisico().length()-4);									
				imagen.setNombreArchivoFisico(imagen.getIdElemento()+ext);
				
				File arch = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images/Expo_" + idExpoSel + "/img/"+imagen.getNombreArchivoFisico());
				try {
					arch.createNewFile();
					FileCopyUtils.copy(imagen.getArchivo(), arch);
					
				} catch (IOException e) {
					e.printStackTrace();
				}				
				
				try {
					Marco.transformar(arch);
				} catch (IOException e) {
					e.printStackTrace();
				}
				imagen.setNombreArchivoFisico("img"+imagen.getIdElemento()+".png");
				imagen.setUrl(arch.getAbsolutePath().replaceAll(ext, ".png"));
				recursoNegocio.save(imagen);			
			}
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdElemento());
	}

	@SkipValidation
	public String edit() {
		

		return "edit";
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del Elemento"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del Elemento") })
	public String update() {
		addActionMessage("El elemento "+ model.getNombre()+" ha sido modificado correctamente.");
		elementoNegocio.save(model);
		
		byte[] bytes = null;

		
		if (fileSnd != null) {
			bytes = new byte[(int) fileSnd.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(fileSnd);
				fileInputStream.read(bytes);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			sonido.setIdElemento(model.getIdElemento());
			sonido.setNombreArchivoFisico(filenameSnd);
			sonido.setArchivo(bytes);			
			
			File dir=new File("Repo");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dirM = new File("Repo/Museo_" + exposicion.getIdMuseo());
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			File dirS = new File("Repo/Museo_" + exposicion.getIdMuseo()
					+ "/sounds");
			if (!dirS.exists()) {
				dirS.mkdir();
			}
			File dirE = new File("Repo/Museo_" + exposicion.getIdMuseo()
					+ "/sounds/Expo_" + idExpoSel);
			if (!dirE.exists()) {
				dirE.mkdir();
			}					

			File archDef = new File("Repo/Museo_" + exposicion.getIdMuseo()
					+ "/sounds/Expo_" + idExpoSel + "/" + sonido.getIdElemento()+".mp3");
			try {
				archDef.createNewFile();
				FileCopyUtils.copy(sonido.getArchivo(), archDef);
			} catch (IOException e) {
				e.printStackTrace();
			}

			sonido.setUrl(archDef.getAbsolutePath());
			recursoNegocio.save(sonido);
		}
								
			bytes = null;

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
			
				imagen.setIdElemento(model.getIdElemento());
				imagen.setNombreArchivoFisico(filenameImg);
				imagen.setArchivo(bytes);			
								
				File dirI = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images");
				if (!dirI.exists()) {
					dirI.mkdir();
				}
				File dirEx = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images/Expo_" + idExpoSel);
				if (!dirEx.exists()) {
					dirEx.mkdir();
				}
				File dirIm = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images/Expo_" + idExpoSel+"/img");
				if (!dirIm.exists()) {
					dirIm.mkdir();
				}
				
				String ext=imagen.getNombreArchivoFisico().substring(imagen.getNombreArchivoFisico().length()-4);									
				imagen.setNombreArchivoFisico(imagen.getIdElemento()+ext);

				File arch = new File("Repo/Museo_" + exposicion.getIdMuseo()
						+ "/images/Expo_" + idExpoSel + "/img/" + imagen.getNombreArchivoFisico());
				try {
					arch.createNewFile();
					FileCopyUtils.copy(imagen.getArchivo(), arch);
				} catch (IOException e) {
					e.printStackTrace();
				}
								
				try {
					Marco.transformar(arch);
				} catch (IOException e) {
					e.printStackTrace();
				}
				imagen.setNombreArchivoFisico("img"+imagen.getIdElemento()+".png");
				imagen.setUrl(arch.getAbsolutePath().replaceAll(ext, ".png"));
				recursoNegocio.save(imagen);
			}
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		Recurso recursoPrueba = new Recurso();
		recursoPrueba.setIdElemento(model.getIdElemento());
		listRecurso = recursoNegocio.findByExample(recursoPrueba);
		return "deleteConfirm";
	}

	public void validateDestroy() {

	}

	public String destroy() {
		for (Recurso recurso : model.getRecursos()) {
			File borraArchivo = new File(recurso.getUrl());
			borraArchivo.delete();
			recursoNegocio.delete(recurso);
			
		}
		addActionMessage("El elemento "+ model.getNombre()+" ha sido eliminado correctamente.");
		elementoNegocio.delete(model);
		return "success";
	}

	/***************************************************************
	 * 
	 */

	public void setModel(Elemento model) {
		this.model = model;
	}

	public Elemento getModel() {

		if (model == null) {
			model = new Elemento();
		}
		return this.model;
	}

	public List<Elemento> getList() {
		return list;
	}

	public void setList(List<Elemento> elementos) {
		this.list = elementos;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = elementoNegocio.findById(idSel);
			
			if(model.getRecursos().size()>0){
				System.out.println("mayor a cero");
			sonido=model.getRecursos().get(0);
			}else{
				sonido=new Recurso();
			}
			
			if(model.getRecursos().size()>1){
				System.out.println("mayor a uno");
			imagen=model.getRecursos().get(1);
			}else{
				imagen=new Recurso();
			}
		}
	}

	public ElementoNegocio getElementoNegocio() {
		return elementoNegocio;
	}

	public void setElementoNegocio(ElementoNegocio elementoNegocio) {
		this.elementoNegocio = elementoNegocio;
	}

	public Integer getIdExpoSel() {
		return idExpoSel;
	}

	public void setIdExpoSel(Integer idExpoSel) {
		if (idExpoSel != null) {
			exposicion = exposicionNegocio.findById(idExpoSel);
			idMuseoSel = exposicion.getIdMuseo();
		}
		this.idExpoSel = idExpoSel;
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

	public RecursoNegocio getRecursoNegocio() {
		return recursoNegocio;
	}

	public void setRecursoNegocio(RecursoNegocio recursoNegocio) {
		this.recursoNegocio = recursoNegocio;
	}

	public List<Recurso> getListRecurso() {
		return listRecurso;
	}

	public void setListRecurso(List<Recurso> listRecurso) {
		this.listRecurso = listRecurso;
	}

	public Integer getIdMuseoSel() {
		return idMuseoSel;
	}

	public void setIdMuseoSel(Integer idMuseoSel) {
		this.idMuseoSel = idMuseoSel;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public List<Recurso> getRecursosTemporales() {
		return recursosTemporales;
	}

	public void setRecursosTemporales(List<Recurso> recursosTemporales) {
		this.recursosTemporales = recursosTemporales;
	}

	public List<Integer> getPorEliminar() {
		return porEliminar;
	}

	public void setPorEliminar(List<Integer> porEliminar) {
		this.porEliminar = porEliminar;
	}

	public Recurso getSonido() {
		if (sonido == null) {			
			sonido = new Recurso();
		}
		return this.sonido;
	}

	public void setSonido(Recurso sonido) {
		this.sonido = sonido;
	}

	public Recurso getImagen() {
		if (imagen == null) {			
			imagen = new Recurso();
		}
		return this.imagen;
	}

	public void setImagen(Recurso imagen) {
		this.imagen = imagen;
	}
/**
 ***********************************************************************
 * 
 */
	public File getFileSnd() {
		return fileSnd;
	}

	public void setFileSnd(File file) {
		this.fileSnd = file;
	}

	public String getFilenameSnd() {
		return filenameSnd;
	}

	public void setFilenameSnd(String filename) {
		this.filenameSnd = filename;
	}

	public String getContentTypeSnd() {
		return contentTypeSnd;
	}

	public void setContentTypeSnd(String contentType) {
		this.contentTypeSnd = contentType;
	}

	public void setArchivoSonido(File file) {
		this.fileSnd = file;
	}

	public void setArchivoSonidoContentType(String contentType) {
		this.contentTypeSnd = contentType;
	}

	public void setArchivoSonidoFileName(String filename) {
		this.filenameSnd = filename;
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
