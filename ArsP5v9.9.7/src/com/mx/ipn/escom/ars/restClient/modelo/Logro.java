package com.mx.ipn.escom.ars.restClient.modelo;

public class Logro {
	private Integer idLogro;
	private String nbLogro;
	private String dsLogro;
	public Logro() {
		
	}
	public Logro(Integer idLogro, String nbLogro, String dsLogro) {
		this.idLogro = idLogro;
		this.nbLogro = nbLogro;
		this.dsLogro = dsLogro;
	}
	public Integer getIdLogro() {
		return idLogro;
	}
	public void setIdLogro(Integer idLogro) {
		this.idLogro = idLogro;
	}
	public String getNbLogro() {
		return nbLogro;
	}
	public void setNbLogro(String nbLogro) {
		this.nbLogro = nbLogro;
	}
	public String getDsLogro() {
		return dsLogro;
	}
	public void setDsLogro(String dsLogro) {
		this.dsLogro = dsLogro;
	}
	
}
