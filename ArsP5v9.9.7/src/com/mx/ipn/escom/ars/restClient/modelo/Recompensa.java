package com.mx.ipn.escom.ars.restClient.modelo;

public class Recompensa {
	private Integer idRecompensa;
	private Integer idLogro;
	private String nbRecompensa;
	private String urlRecompensa;
	private String urlTextura;
	
	public Recompensa() {
	}

	public Recompensa(Integer idRecompensa,Integer idLogro, String nbRecompensa,
			String urlRecompensa, String urlTextura) {
		this.idRecompensa = idRecompensa;
		this.idLogro = idLogro;
		this.nbRecompensa = nbRecompensa;
		this.urlRecompensa = urlRecompensa;
		this.urlTextura = urlTextura;
	}

	public Integer getIdRecompensa() {
		return idRecompensa;
	}

	public void setIdRecompensa(Integer idRecompensa) {
		this.idRecompensa = idRecompensa;
	}
	public Integer getIdLogro() {
		return idLogro;
	}

	public void setIdLogro(Integer idLogro) {
		this.idLogro = idLogro;
	}

	public String getNbRecompensa() {
		return nbRecompensa;
	}

	public void setNbRecompensa(String nbRecompensa) {
		this.nbRecompensa = nbRecompensa;
	}

	public String getUrlRecompensa() {
		return urlRecompensa;
	}

	public void setUrlRecompensa(String urlRecompensa) {
		this.urlRecompensa = urlRecompensa;
	}

	public String getUrlTextura() {
		return urlTextura;
	}

	public void setUrlTextura(String urlTextura) {
		this.urlTextura = urlTextura;
	}

}
