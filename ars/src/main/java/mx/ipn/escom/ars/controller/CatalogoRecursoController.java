package mx.ipn.escom.ars.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.ars.modelo.Elemento;
import mx.ipn.escom.ars.modelo.Museo;
import mx.ipn.escom.ars.modelo.Recurso;
import mx.ipn.escom.ars.modelo.Subtema;
import mx.ipn.escom.ars.negocio.ElementoNegocio;
import mx.ipn.escom.ars.negocio.RecursoNegocio;
import mx.ipn.escom.ars.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-museo" }) })

public class CatalogoRecursoController extends ActionSupport implements
		ModelDriven<Recurso> {

	private static final long serialVersionUID = -7107264188963726964L;
	private RecursoNegocio recursoNegocio;
	private Recurso model = null;
	private Integer idSel = null;
	private List<Recurso> list = null;
	private Integer idElementoSel;
	private Integer idExpoSel;

	private File fileDat = null;
	private String filenameDat;
	private String contentTypeDat;
	
	private File fileXml = null;
	private String filenameXml;
	private String contentTypeXml;

	private String accion = null;
	

	private ElementoNegocio elementoNegocio;
	private Elemento elemento;

	

	@SkipValidation
	public String editNew() {	
		
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.RECURSO, "Repo/Museos/imgMuseos.zip");
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.CARPETA, "Repo/Museos/img");
		
		return "editNew";
	}
	
	public String create() {

		byte[] bytesDat = null;
		byte[] bytesXml = null;
		
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
			File dirM = new File("Repo/Museos");
			if (!dirM.exists()) {
				dirM.mkdir();
			}
			
			File dirR = new File("Repo/Museos/Recursos");
			if (!dirR.exists()) {
				dirR.mkdir();
			}
			
			File archDat = new File("Repo/Museos/Recursos/museos.dat" );
			File archXml = new File("Repo/Museos/Recursos/museos.xml" );
			
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
		
		
		return "success";
	}

	/****************************************************************/

	public void setModel(Recurso model) {
		this.model = model;
	}

	public Recurso getModel() {

		if (model == null) {
			model = new Recurso();
		}
		return this.model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = recursoNegocio.findById(idSel);
		}
	}

	public Integer getIdElementoSel() {
		return idElementoSel;
	}

	public void setIdElementoSel(Integer idElementoSel) {
		if (idElementoSel != null) {
			elemento = elementoNegocio.findById(idElementoSel);
			idExpoSel = elemento.getIdExposicion();
		}
		this.idElementoSel = idElementoSel;
	}

	public RecursoNegocio getRecursoNegocio() {
		return recursoNegocio;
	}

	public void setRecursoNegocio(RecursoNegocio recursoNegocio) {
		this.recursoNegocio = recursoNegocio;
	}

	public List<Recurso> getList() {
		return list;
	}

	public void setList(List<Recurso> list) {
		this.list = list;
	}

	public ElementoNegocio getElementoNegocio() {
		return elementoNegocio;
	}

	public void setElementoNegocio(ElementoNegocio elementoNegocio) {
		this.elementoNegocio = elementoNegocio;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	public Integer getIdExpoSel() {
		return idExpoSel;
	}

	public void setIdExpoSel(Integer idExpoSel) {
		this.idExpoSel = idExpoSel;
	}


	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
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
