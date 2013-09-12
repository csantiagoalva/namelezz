package mx.ipn.escom.ars.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mx.ipn.escom.ars.util.IntegerAdapter;

@XmlRootElement
@Entity
@Table(name = "Subtema")
public class Subtema {
		
	private Integer idSubtema;
	private String nombre;
	private String descripcion;
	
	private List<Exposicion> exposiciones;
	private List<ExpoSubtema> expoSubtemas;

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_subtema")
	public Integer getIdSubtema() {
		return idSubtema;
	}
	public void setIdSubtema(Integer idSubtema) {
		this.idSubtema = idSubtema;
	}
	
	@Column(name = "nb_subtema")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "ds_subtema")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}
	
	@XmlTransient
	@ManyToMany
	@JoinTable(name = "ExposicionSubtema", joinColumns = { @JoinColumn(name = "id_subtema") }, inverseJoinColumns = { @JoinColumn(name = "id_exposicion") })
	public List<Exposicion> getExposiciones() {
		return exposiciones;
	}
	public void setExposiciones(List<Exposicion> exposiciones) {
		this.exposiciones = exposiciones;
	}
	@XmlTransient
	@OneToMany(mappedBy = "subtema")
	public List<ExpoSubtema> getExpoSubtemas() {
		return expoSubtemas;
	}
	public void setExpoSubtemas(List<ExpoSubtema> expoSubtemas) {
		this.expoSubtemas = expoSubtemas;
	}
	
	
				
}