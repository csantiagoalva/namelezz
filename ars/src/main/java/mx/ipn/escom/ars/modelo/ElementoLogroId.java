package mx.ipn.escom.ars.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class ElementoLogroId implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4479433019715210528L;
	private Integer idElemento=null;
	private Integer idLogro=null;
				
	public ElementoLogroId() {	
	}
	public ElementoLogroId(Integer idElemento, Integer idSub) {
		this.idElemento=idElemento;
		this.idLogro=idSub;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}
	@Column(name = "id_elemento", nullable = false)			
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}
	@Column(name = "id_logro", nullable = false)
	public Integer getIdLogro() {
		return idLogro;
	}
	public void setIdLogro(Integer idLogro) {
		this.idLogro = idLogro;
	}
	
	
	
}
