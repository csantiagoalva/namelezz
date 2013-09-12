package com.mx.ipn.escom.ars.restClient.modelo;

public class Elemento {
	private Integer	idElemento;
	private String nbElemento;
	private Integer idExposicion;
	private String dsElemento;
	
	public Elemento() {
	}
	public Elemento(Integer idElemento, String nbElemento, Integer idExposicion,
			String dsElemento) {
		this.idElemento = idElemento;
		this.nbElemento = nbElemento;
		this.idExposicion = idExposicion;
		this.dsElemento = dsElemento;
	}
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}
	public String getNbElemento() {
		return nbElemento;
	}
	public void setNbElemento(String nbElemento) {
		this.nbElemento = nbElemento;
	}
	public Integer getIdExposicion() {
		return idExposicion;
	}
	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}
	public String getDsElemento() {
		return dsElemento;
	}
	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}
	
	
}
