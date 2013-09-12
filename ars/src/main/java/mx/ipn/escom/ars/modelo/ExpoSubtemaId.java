package mx.ipn.escom.ars.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class ExpoSubtemaId implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4479433019715210528L;
	private Integer idExpo=null;
	private Integer idSubtema=null;
				
	public ExpoSubtemaId() {	
	}
	public ExpoSubtemaId(Integer idExpo, Integer idSub) {
		this.idExpo=idExpo;
		this.idSubtema=idSub;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}
	@Column(name = "id_exposicion", nullable = false)			
	public Integer getIdExpo() {
		return idExpo;
	}
	public void setIdExpo(Integer idExpo) {
		this.idExpo = idExpo;
	}
	@Column(name = "id_subtema", nullable = false)
	public Integer getIdSubtema() {
		return idSubtema;
	}
	public void setIdSubtema(Integer idSubtema) {
		this.idSubtema = idSubtema;
	}
	
	
	
}
