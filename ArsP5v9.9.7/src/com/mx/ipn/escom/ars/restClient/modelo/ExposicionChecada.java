package com.mx.ipn.escom.ars.restClient.modelo;

public class ExposicionChecada {
	private Integer idExposicion;
	private Integer rkExposicion;
	private Integer enviada;
	public ExposicionChecada() {
	}
	public ExposicionChecada(Integer idExposicion, Integer rkExposicion,Integer enviada) {
		this.idExposicion = idExposicion;
		this.rkExposicion = rkExposicion;
		this.enviada=enviada;
	}
	public Integer getIdExposicion() {
		return idExposicion;
	}
	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}
	public Integer getRkExposicion() {
		return rkExposicion;
	}
	public void setRkExposicion(Integer rkExposicion) {
		this.rkExposicion = rkExposicion;
	}
	public Integer getEnviada() {
		return enviada;
	}
	public void setEnviada(Integer enviada) {
		this.enviada = enviada;
	}
	
}
