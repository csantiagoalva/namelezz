package com.mx.ipn.escom.ars.restClient.modelo;

public class Recurso {
	private Integer idRecurso;
	private Integer idElemento;
	private String nbElemento;
	private String descElemento;
	private String nbArchivoFisico;
	private String urlArchivo;
	
	public Recurso() {
	}
	public Recurso(Integer idRecurso, Integer idElemento, String nbElemento,
			String descElemento, String nbArchivoFisico, String urlArchivo) {
		this.idRecurso = idRecurso;
		this.idElemento = idElemento;
		this.nbElemento = nbElemento;
		this.descElemento = descElemento;
		this.nbArchivoFisico = nbArchivoFisico;
		this.urlArchivo = urlArchivo;
	}
	public Integer getIdRecurso() {
		return idRecurso;
	}
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
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
	public String getDescElemento() {
		return descElemento;
	}
	public void setDescElemento(String descElemento) {
		this.descElemento = descElemento;
	}
	public String getNbArchivoFisico() {
		return nbArchivoFisico;
	}
	public void setNbArchivoFisico(String nbArchivoFisico) {
		this.nbArchivoFisico = nbArchivoFisico;
	}
	public String getUrlArchivo() {
		return urlArchivo;
	}
	public void setUrlArchivo(String urlArchivo) {
		this.urlArchivo = urlArchivo;
	}
	
}
