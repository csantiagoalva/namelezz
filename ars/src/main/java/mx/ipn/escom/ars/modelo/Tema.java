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
@Table(name = "Tema")
public class Tema {
	private Integer idTema;
	private String nombre;
	private String descripcion;
	private List<Museo> museos;
	private List<MuseoTema> museoTemas;
	

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_tema")
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
	
	@Column(name = "nb_tema")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "ds_tema")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@XmlTransient
	@ManyToMany
	@JoinTable(name = "MuseoTema", joinColumns = { @JoinColumn(name = "id_tema") }, inverseJoinColumns = { @JoinColumn(name = "id_museo") })
	public List<Museo> getMuseos() {
		return museos;
	}
	public void setMuseos(List<Museo> museos) {
		this.museos = museos;
	}
	@XmlTransient
	@OneToMany(mappedBy = "tema")
	public List<MuseoTema> getMuseoTemas() {
		return museoTemas;
	}
	public void setMuseoTemas(List<MuseoTema> museoTemas) {
		this.museoTemas = museoTemas;
	}
	
	
	
		
}