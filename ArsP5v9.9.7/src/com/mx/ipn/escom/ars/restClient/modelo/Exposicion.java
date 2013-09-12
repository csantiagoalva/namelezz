package com.mx.ipn.escom.ars.restClient.modelo;

public class Exposicion {
	private Integer idExposicion;
	private Integer idMuseo;
	private String nbExposicion;
	private String descExposicion;
	private Double rkExposcion;
	private Integer noCalificaciones;
	private String rpExposicion;
	private String edoExposicion;
	private String fhInicio;
	private String fhFin;

	public Exposicion() {
	}

	public Exposicion(Integer idExposicion, Integer idMuseo,
			String nbExposicion, String descExposicion, Double rkExposcion,
			String rpExposicion, String edoExposicion, String fhInicio,
			String fhFin,Integer noCalificaciones) {
		this.idExposicion = idExposicion;
		this.idMuseo = idMuseo;
		this.nbExposicion = nbExposicion;
		this.descExposicion = descExposicion;
		this.rkExposcion = rkExposcion;
		this.rpExposicion = rpExposicion;
		this.edoExposicion = edoExposicion;
		this.fhInicio = fhInicio;
		this.fhFin = fhFin;
		this.noCalificaciones=noCalificaciones;
	}

	public Integer getIdExposicion() {
		return idExposicion;
	}	

	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}

	public Integer getIdMuseo() {
		return idMuseo;
	}

	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}

	public String getNbExposicion() {
		return nbExposicion;
	}

	public void setNbExposicion(String nbExposicion) {
		this.nbExposicion = nbExposicion;
	}

	public String getDescExposicion() {
		return descExposicion;
	}

	public void setDescExposicion(String descExposicion) {
		this.descExposicion = descExposicion;
	}

	public Double getRkExposcion() {
		return rkExposcion;
	}

	public void setRkExposcion(Double rkExposcion) {
		this.rkExposcion = rkExposcion;
	}

	public String getRpExposicion() {
		return rpExposicion;
	}

	public void setRpExposicion(String rpExposicion) {
		this.rpExposicion = rpExposicion;
	}

	public String getEdoExposicion() {
		return edoExposicion;
	}

	public void setEdoExposicion(String edoExposicion) {
		this.edoExposicion = edoExposicion;
	}

	public String getFhInicio() {
		return fhInicio;
	}

	public void setFhInicio(String fhInicio) {
		this.fhInicio = fhInicio;
	}

	public String getFhFin() {
		return fhFin;
	}

	public void setFhFin(String fhFin) {
		this.fhFin = fhFin;
	}
	public Integer getNoCalificaciones() {
		return noCalificaciones;
	}

	public void setNoCalificaciones(Integer noCalificaciones) {
		this.noCalificaciones = noCalificaciones;
	}

}
