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
//import org.directwebremoting.annotations.RemoteProperty;

@XmlRootElement
@Entity
@Table(name = "MuseoTema")
public class MuseoTema {
	private Integer idMuseo;
	private Integer idTema;
	
	private Tema tema;
	private Museo museo;
	private MuseoTemaId museoTemaId;
	
	
	public MuseoTema(){}
	public MuseoTema(Integer idMuseo, Integer idTema){
		this.museoTemaId=new MuseoTemaId();
		this.museoTemaId.setIdMuseo(idMuseo);
		this.museoTemaId.setIdTema(idTema);
	}
	
	public MuseoTema(MuseoTemaId museoTemaId) {
		this.museoTemaId=museoTemaId;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "museo",
				"tema");
	}
	@Column(name = "id_museo", insertable=false, updatable=false)
	public Integer getIdMuseo() {
		return idMuseo;
	}
	
	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}
	@Column(name = "id_tema", insertable=false, updatable=false)
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	@EmbeddedId
	public MuseoTemaId getMuseoTemaId() {
		return museoTemaId;
	}
	public void setMuseoTemaId(MuseoTemaId museoTemaId) {
		this.museoTemaId = museoTemaId;
	}
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "id_museo", referencedColumnName = "id_museo", insertable = false, updatable = false)
	public Museo getMuseo() {
		return museo;
	}
	public void setMuseo(Museo museo) {
		this.museo = museo;
	}
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "id_tema", referencedColumnName = "id_tema", insertable = false, updatable = false)
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}		
	
	
}
