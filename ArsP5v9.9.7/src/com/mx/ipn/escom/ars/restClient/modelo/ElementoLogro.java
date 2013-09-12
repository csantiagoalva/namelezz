package com.mx.ipn.escom.ars.restClient.modelo;

public class ElementoLogro {
	private Integer idLogro;
	private Integer idElemento;
	public ElementoLogro() {
		
	}
	public ElementoLogro(Integer idLogro, Integer idElemento) {
		this.idLogro = idLogro;
		this.idElemento = idElemento;
	}
	public Integer getIdLogro() {
		return idLogro;
	}
	public void setIdLogro(Integer idLogro) {
		this.idLogro = idLogro;
	}
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}
}
