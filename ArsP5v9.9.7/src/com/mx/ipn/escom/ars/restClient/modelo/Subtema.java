package com.mx.ipn.escom.ars.restClient.modelo;

public class Subtema {
	private Integer idSubtema;
	private String nbSubtema;
	private String dsSubtema;
	
	public Subtema() {
	}
	public Subtema(Integer idSubtema, String nbSubtema, String dsSubtema) {
		this.idSubtema = idSubtema;
		this.nbSubtema = nbSubtema;
		this.dsSubtema = dsSubtema;
	}
	public Integer getIdSubtema() {
		return idSubtema;
	}
	public void setIdSubtema(Integer idSubtema) {
		this.idSubtema = idSubtema;
	}
	public String getNbSubtema() {
		return nbSubtema;
	}
	public void setNbSubtema(String nbSubtema) {
		this.nbSubtema = nbSubtema;
	}
	public String getDsSubtema() {
		return dsSubtema;
	}
	public void setDsSubtema(String dsSubtema) {
		this.dsSubtema = dsSubtema;
	}
	
}
