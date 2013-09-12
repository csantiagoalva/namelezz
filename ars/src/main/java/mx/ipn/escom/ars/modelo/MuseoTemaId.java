package mx.ipn.escom.ars.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class MuseoTemaId implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6939513911005249280L;

	
	private Integer idMuseo=null;
	private Integer idTema=null;	
	public MuseoTemaId(){
		
	}
	
	public MuseoTemaId(Integer idm, Integer idt){
		idMuseo=idm;
		idTema=idt;
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}

	@Column(name = "id_museo", nullable = false)
	public Integer getIdMuseo() {
		return idMuseo;
	}

	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}
	@Column(name = "id_tema", nullable = false)
	public Integer getIdTema() {
		return idTema;
	}

	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	
	
}
