package com.mx.ipn.escom.ars.restClient.modelo;

public class Tema {
	private Integer idTema;
	private String nbTema;
	private String dsTema;
		
	public Tema() {
	}
	public Tema(Integer idTema, String nbTema, String dsTema) {
		this.idTema = idTema;
		this.nbTema = nbTema;
		this.dsTema = dsTema;
	}
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	public String getNbTema() {
		return nbTema;
	}
	public void setNbTema(String nbTema) {
		this.nbTema = nbTema;
	}
	public String getDsTema() {
		return dsTema;
	}
	public void setDsTema(String dsTema) {
		this.dsTema = dsTema;
	}	
}
