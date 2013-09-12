package com.mx.ipn.escom.ars.restClient.modelo;

public class ExposicionSubtema {
	private Integer idExposicion;
	private Integer idSubtema;
	
	public ExposicionSubtema() {
	}
	public ExposicionSubtema(Integer idExposicion, Integer idSubtema) {
		this.idExposicion = idExposicion;
		this.idSubtema = idSubtema;
	}
	public Integer getIdExposicion() {
		return idExposicion;
	}
	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}
	public Integer getIdSubtema() {
		return idSubtema;
	}
	public void setIdSubtema(Integer idSubtema) {
		this.idSubtema = idSubtema;
	}
	
}
