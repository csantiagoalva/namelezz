package com.mx.ipn.escom.ars.restClient.modelo;

public class LogroVista {	
	public String logroId;
	public String logroNombre;
	public String logroDesc;

	public LogroVista(final String nombre, final String id, final String desc) {
		logroId = id;
		logroNombre = nombre;
		logroDesc=desc;
	}

}