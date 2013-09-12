package mx.ipn.escom.ars.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "Exposicion")
public class Exposicion {
	private Integer idExposicion;
	private String nombre;
	private Double ranking;
	private Integer idMuseo;
	private String descripcion;
	private String tipo;
	private String edoExposicion;
	private String fhInicio;
	private String fhFin;
	private Integer numCalificaciones;

	private Museo museo;
	private List<Elemento> elementos;
	private List<Subtema> subtemas;
	private List<ExpoSubtema> expoSubtemas;

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_exposicion")
	public Integer getIdExposicion() {
		return idExposicion;
	}

	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}

	@Column(name = "nb_exposicion")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "rk_exposicion")
	public Double getRanking() {
		return ranking;
	}

	public void setRanking(Double ranking) {
		this.ranking = ranking;
	}

	@Column(name = "id_museo")
	public Integer getIdMuseo() {
		return idMuseo;
	}

	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}

	@Column(name = "desc_exposicion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "tp_exposicion")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "edo_exposicion")
	public String getEdoExposicion() {
		return edoExposicion;
	}

	public void setEdoExposicion(String edoExposicion) {
		this.edoExposicion = edoExposicion;
	}

	@Column(name = "fh_inicio")
	public String getFhInicio() {
		return fhInicio;
	}

	public void setFhInicio(String fhInicio) {
		this.fhInicio = fhInicio;
	}

	@Column(name = "fh_fin")
	public String getFhFin() {
		return fhFin;
	}

	public void setFhFin(String fhFin) {
		this.fhFin = fhFin;
	}
	
	@Column(name = "no_calificaciones")
	public Integer getNumCalificaciones() {
		return numCalificaciones;
	}

	public void setNumCalificaciones(Integer numCalificaciones) {
		this.numCalificaciones = numCalificaciones;
	}

	@XmlTransient
	@OneToMany(mappedBy = "exposicion")
	public List<Elemento> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	@XmlTransient
	@ManyToMany
	@JoinTable(name = "ExposicionSubtema", joinColumns = { @JoinColumn(name = "id_exposicion") }, inverseJoinColumns = { @JoinColumn(name = "id_subtema") })
	public List<Subtema> getSubtemas() {
		return subtemas;
	}

	public void setSubtemas(List<Subtema> subtemas) {
		this.subtemas = subtemas;
	}

	@XmlTransient
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_museo", referencedColumnName = "id_museo", insertable = false, updatable = false) })
	public Museo getMuseo() {
		return museo;
	}

	public void setMuseo(Museo museo) {
		this.museo = museo;
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