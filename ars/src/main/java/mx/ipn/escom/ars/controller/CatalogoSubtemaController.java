


package mx.ipn.escom.ars.controller;

import java.util.List;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import mx.ipn.escom.ars.modelo.Subtema;

import mx.ipn.escom.ars.negocio.SubtemaNegocio;
import mx.ipn.escom.ars.negocio.TemaNegocio;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-subtema" }) })
public class CatalogoSubtemaController  extends ActionSupport implements
ModelDriven<Subtema>{


	private Subtema model;
	private SubtemaNegocio subtemaNegocio;
	private TemaNegocio temaNegocio;
	private List<Subtema> list;
	private Integer idSel;


	@SkipValidation
	public HttpHeaders index() {				
		list = subtemaNegocio.findAll();
		for(Subtema aux: list){
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

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Introduzca el nombre del Subtema."),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "Introduzca la descripción del Subtema.")})
	public HttpHeaders create() {
		addActionMessage("El subtema "+ model.getNombre()+" ha sido agregado correctamente.");
		model=subtemaNegocio.save(model);
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdSubtema());
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
		
		addActionMessage("El subtema "+ model.getNombre()+" ha sido modificado correctamente.");
		subtemaNegocio.save(model);

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
		addActionMessage("El subtema "+ model.getNombre()+" ha sido elmiminado");
		subtemaNegocio.delete(model);
		return "success";
	}




	/////////////////////////////////////////////////////////////////////////////////////////////////
	public Subtema getModel() {
		if(model==null)
			model=new Subtema();
		return model;
	}

	public void setModel(Subtema model) {

		this.model = model;
	}

	public SubtemaNegocio getSubtemaNegocio() {
		return subtemaNegocio;
	}

	public void setSubtemaNegocio(SubtemaNegocio SubtemaNegocio) {
		this.subtemaNegocio = SubtemaNegocio;
	}

	public List<Subtema> getList() {
		return list;
	}

	public void setList(List<Subtema> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;		
		if (idSel != null) {
			model = subtemaNegocio.findById(idSel);
		}
	}

	public TemaNegocio getTemaNegocio() {
		return temaNegocio;
	}

	public void setTemaNegocio(TemaNegocio temaNegocio) {
		this.temaNegocio = temaNegocio;
	}



}
