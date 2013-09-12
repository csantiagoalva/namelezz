package com.mx.ipn.escom.ars.restClient.modelo;

public class ExposicionVista {
	public String expoNombre;
	public String expoDesc;
	public String fechaInicio;
	public String fechaFin;
	public String museoNombre;
	public String expoId;

	public ExposicionVista(String nombre, String id){
		expoNombre=nombre;
		expoId=id;		
	}

	public ExposicionVista() {
	}

	public ExposicionVista(String expoNombre, String expoDesc,
			String fechaInicio, String fechaFin, String museoNombre) {
		this.expoNombre = expoNombre;
		this.expoDesc = expoDesc;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.museoNombre = museoNombre;
	}

	public ExposicionVista(String expoNombre, String expoDesc,
			String fechaInicio, String fechaFin, String museoNombre,
			String expoId) {
		super();
		this.expoNombre = expoNombre;
		this.expoDesc = expoDesc;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.museoNombre = museoNombre;
		this.expoId = expoId;
	}
	
}

