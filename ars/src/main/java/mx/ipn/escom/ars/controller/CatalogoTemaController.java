package mx.ipn.escom.ars.controller;

import java.util.List;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import mx.ipn.escom.ars.modelo.Tema;
import mx.ipn.escom.ars.negocio.SubtemaNegocio;
import mx.ipn.escom.ars.negocio.TemaNegocio;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-tema" }) })
public class CatalogoTemaController extends ActionSupport implements
ModelDriven<Tema>{


	private Tema model;
	private TemaNegocio temaNegocio;
	private SubtemaNegocio subtemaNegocio;
	private List<Tema> list;
	private Integer idSel;


	@SkipValidation
	public HttpHeaders index() {				
		list = temaNegocio.findAll();
		for(Tema aux: list){
			System.out.println("assd "+aux.getNombre());			
		}
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {

		return "editNew";
	}

	public void validateCreate() {
		if(temaNegocio.existe(model.getNombre())){
			addActionError("Ya existe un tema llamado "+model.getNombre());
		}
		if(subtemaNegocio.existe(model.getNombre())){
			addActionError("Ya existe un subtema llamado "+model.getNombre());
		}
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del tema."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del tema.")})
	public HttpHeaders create() {
		addActionMessage("El tema "+ model.getNombre()+" ha sido agregadoo correctamente.");
		model=temaNegocio.save(model);
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdTema());
	}



	@SkipValidation
	public String edit() {
		System.out.println("EN edit() ");
		return "edit";
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del museo."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripcion del museo.")})
	@SkipValidation
	public String update() {
		System.out.println("EN update() ");
		System.out.println("EN update "+model.getNombre());
		addActionMessage("El tema "+ model.getNombre()+" ha sido modificado correctamente.");
		temaNegocio.save(model);
		
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
		addActionMessage("El tema "+ model.getNombre()+" ha sido elmiminado");
		temaNegocio.delete(model);
		return "success";
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public Tema getModel() {
		if(model==null)
			model=new Tema();
		return model;
	}

	public void setModel(Tema model) {

		this.model = model;
	}

	public TemaNegocio getTemaNegocio() {
		return temaNegocio;
	}

	public void setTemaNegocio(TemaNegocio temaNegocio) {
		this.temaNegocio = temaNegocio;
	}

	public List<Tema> getList() {
		return list;
	}

	public void setList(List<Tema> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;		
		if (idSel != null) {
			model = temaNegocio.findById(idSel);
		}
	}

	public SubtemaNegocio getSubtemaNegocio() {
		return subtemaNegocio;
	}

	public void setSubtemaNegocio(SubtemaNegocio subtemaNegocio) {
		this.subtemaNegocio = subtemaNegocio;
	}



}
