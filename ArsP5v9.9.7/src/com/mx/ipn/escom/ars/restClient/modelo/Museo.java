package com.mx.ipn.escom.ars.restClient.modelo;

public class Museo {
	private Integer idMuseo;
	private String nbMuseo;
	private String descMuseo;
	private String dirCalle;
	private String dirNumero;
	private String dirColonia;
	private String dirDelegacion;
	private String dirEstado;
	private Integer dirCP;
	private String contacto;
	private String edoMuseo;	
	
	public Museo() {
	}
	public Museo(Integer idMuseo, String nbMuseo, String descMuseo,
			String dirCalle, String dirNumero, String dirColonia,
			String dirDelegacion, String dirEstado, Integer dirCP,
			String contacto, String edoMuseo) {
		this.idMuseo = idMuseo;
		this.nbMuseo = nbMuseo;
		this.descMuseo = descMuseo;
		this.dirCalle = dirCalle;
		this.dirNumero = dirNumero;
		this.dirColonia = dirColonia;
		this.dirDelegacion = dirDelegacion;
		this.dirEstado = dirEstado;
		this.dirCP = dirCP;
		this.contacto = contacto;
		this.edoMuseo = edoMuseo;		
	}
	public Integer getIdMuseo() {
		return idMuseo;
	}
	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}
	public String getNbMuseo() {
		return nbMuseo;
	}
	public void setNbMuseo(String nbMuseo) {
		this.nbMuseo = nbMuseo;
	}
	public String getDescMuseo() {
		return descMuseo;
	}
	public void setDescMuseo(String descMuseo) {
		this.descMuseo = descMuseo;
	}
	public String getDirCalle() {
		return dirCalle;
	}
	public void setDirCalle(String dirCalle) {
		this.dirCalle = dirCalle;
	}
	public String getDirNumero() {
		return dirNumero;
	}
	public void setDirNumero(String dirNumero) {
		this.dirNumero = dirNumero;
	}
	public String getDirColonia() {
		return dirColonia;
	}
	public void setDirColonia(String dirColonia) {
		this.dirColonia = dirColonia;
	}
	public String getDirDelegacion() {
		return dirDelegacion;
	}
	public void setDirDelegacion(String dirDelegacion) {
		this.dirDelegacion = dirDelegacion;
	}
	public String getDirEstado() {
		return dirEstado;
	}
	public void setDirEstado(String dirEstado) {
		this.dirEstado = dirEstado;
	}
	public Integer getDirCP() {
		return dirCP;
	}
	public void setDirCP(Integer dirCP) {
		this.dirCP = dirCP;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getEdoMuseo() {
		return edoMuseo;
	}
	public void setEdoMuseo(String edoMuseo) {
		this.edoMuseo = edoMuseo;
	}	
	
}
