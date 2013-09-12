package mx.ipn.escom.ars.modelo;

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
@Table(name = "Elemento")
public class Elemento {
	private Integer idElemento;
	private String nombre;
	private Integer idExposicion;
	private String descripcion;

	private Exposicion exposicion;
	private List<Recurso> recursos;

	private List<Logro> logros;
	private List<ElementoLogro> elementoLogros;

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_elemento")
	public Integer getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}

	@Column(name = "nb_elemento")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "id_exposicion")
	public Integer getIdExposicion() {
		return idExposicion;
	}

	public void setIdExposicion(Integer idExposicion) {
		this.idExposicion = idExposicion;
	}
	
	@Column(name = "ds_elemento")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@XmlTransient
	@OneToMany(mappedBy = "elemento")
	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	@XmlTransient
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_exposicion", referencedColumnName = "id_exposicion", insertable = false, updatable = false) })
	public Exposicion getExposicion() {
		return exposicion;
	}

	public void setExposicion(Exposicion exposicion) {
		this.exposicion = exposicion;
	}

	@XmlTransient
	@ManyToMany
	@JoinTable(name = "ElementoLogro", joinColumns = { @JoinColumn(name = "id_elemento") }, inverseJoinColumns = { @JoinColumn(name = "id_logro") })
	public List<Logro> getLogros() {
		return logros;
	}

	public void setLogros(List<Logro> logros) {
		this.logros = logros;
	}
	@XmlTransient
	@OneToMany(mappedBy = "elemento")
	public List<ElementoLogro> getElementoLogros() {
		return elementoLogros;
	}

	public void setElementoLogros(List<ElementoLogro> elementoLogros) {
		this.elementoLogros = elementoLogros;
	}

}