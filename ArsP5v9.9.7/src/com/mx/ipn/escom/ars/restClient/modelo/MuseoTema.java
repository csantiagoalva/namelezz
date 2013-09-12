package com.mx.ipn.escom.ars.restClient.modelo;

public class MuseoTema {
	private Integer idMuseo;
	private Integer idTema;
	
	public MuseoTema() {
	}
	public MuseoTema(Integer idMuseo, Integer idTema) {
		this.idMuseo = idMuseo;
		this.idTema = idTema;
	}
	public Integer getIdMuseo() {
		return idMuseo;
	}
	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	
}
