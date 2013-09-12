package mx.ipn.escom.ars.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mx.ipn.escom.ars.util.IntegerAdapter;

@XmlRootElement
@Entity
@Table(name = "Logro")
public class Logro {
	private Integer idLogro;
	private String nombre;
	private String descripcion;
	private List<Elemento> elementos;
	private List<ElementoLogro> elementoLogros;
	
	private List<Recompensa> recompensas;
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_logro")
	public Integer getIdLogro() {
		return idLogro;
	}
	public void setIdLogro(Integer idLogro) {
		this.idLogro = idLogro;
	}
	
	@Column(name = "nb_logro")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "ds_logro")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@XmlTransient
	@ManyToMany
	@JoinTable(name = "ElementoLogro", joinColumns = { @JoinColumn(name = "id_logro") }, inverseJoinColumns = { @JoinColumn(name = "id_elemento") })
	public List<Elemento> getElementos() {
		return elementos;
	}
	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}
	
	@XmlTransient
	@OneToMany(mappedBy="logro")
	public List<ElementoLogro> getElementoLogros() {
		return elementoLogros;
	}
	public void setElementoLogros(List<ElementoLogro> elementoLogros) {
		this.elementoLogros = elementoLogros;
	}
	@XmlTransient	
	@OneToMany(mappedBy = "logro")
	public List<Recompensa> getRecompensas() {
		return recompensas;
	}
	public void setRecompensas(List<Recompensa> recompensas) {
		this.recompensas = recompensas;
	}
			
	

}