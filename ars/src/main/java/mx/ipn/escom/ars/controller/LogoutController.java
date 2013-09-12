package mx.ipn.escom.ars.controller;


import javax.inject.Named;


import mx.ipn.escom.ars.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

@Named
@Results({ @Result(name = "login", location = "/login.jsp") })
public class LogoutController {	

	public String execute() {		
		ActionContext.getContext().getSession()
				.remove(NombreObjetosSesion.USUARIO);
		return "login";
	}

	public String index() {
		return execute();
	}

}