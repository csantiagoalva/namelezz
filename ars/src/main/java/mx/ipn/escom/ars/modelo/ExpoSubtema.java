package mx.ipn.escom.ars.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;

@XmlRootElement
@Entity
@Table(name = "ExposicionSubtema")
public class ExpoSubtema {
	private Integer idExposicion;
	private Integer idSubtema;
	
	private Subtema subtema;
	private Exposicion exposicion;
	private ExpoSubtemaId expoSubtemaId;
	
	
	public ExpoSubtema(){}
	public ExpoSubtema(Integer idExpo, Integer idSubtema){
		this.expoSubtemaId=new ExpoSubtemaId();
		this.expoSubtemaId.setIdExpo(idExposicion);
		this.expoSubtemaId.setIdSubtema(idSubtema);
	}
	
	public ExpoSubtema(ExpoSubtemaId expoSubtemaId) {
		this.expoSubtemaId=expoSubtemaId;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "expo",
				"subtema");
	}
	@Column(name = "id_Exposicion", insertable=false, updatable=false)
	public Integer getIdExposicion() {
		return idExposicion;
	}
	
	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}
	@Column(name = "id_subtema", insertable=false, updatable=false)
	public Integer getIdsubtema() {
		return idSubtema;
	}
	public void setIdsubtema(Integer idsubtema) {
		this.idSubtema = idsubtema;
	}
	@EmbeddedId
	public ExpoSubtemaId getExposubtemaId() {
		return expoSubtemaId;
	}
	public void setExposubtemaId(ExpoSubtemaId expoSubtemaId) {
		this.expoSubtemaId = expoSubtemaId;
	}
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "id_exposicion", referencedColumnName = "id_exposicion", insertable = false, updatable = false)
	public Exposicion getExposicion() {
		return exposicion;
	}
	public void setExposicion(Exposicion exposicion) {
		this.exposicion = exposicion;
	}
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "id_subtema", referencedColumnName = "id_subtema", insertable = false, updatable = false)
	public Subtema getSubtema() {
		return subtema;
	}
	public void setSubtema(Subtema subtema) {
		this.subtema = subtema;
	}		
}
