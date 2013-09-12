package com.mx.ipn.escom.ars.restClient.modelo;

public class Version {
	private Integer id;
	private String tabla;
	private Integer version;
	public Version() {
	}
	public Version(Integer id, String tabla, Integer version) {
		this.id = id;
		this.tabla = tabla;
		this.version = version;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTabla() {
		return tabla;
	}
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
