package mx.ipn.escom.ars.modelo;

import java.io.Serializable;
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
@Table(name = "Museo")
public class Museo  {

	private Integer idMuseo;
	private String nombre;
	private String descripcion;
	private String calle;
	private String numero;
	private String colonia;
	private String delegacion;
	private String estado;
	private Integer codigo;
	private String contacto;
	private String edoMuseo;

	private List<Tema> temas;
	private List<MuseoTema> museoTemas;
	private List<Exposicion> exposiciones;

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_museo")
	public Integer getIdMuseo() {
		return idMuseo;
	}

	public void setIdMuseo(Integer idMuseo) {
		this.idMuseo = idMuseo;
	}

	@Column(name = "nb_museo")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "desc_museo")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "dir_calle")
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name = "dir_numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "dir_colonia")
	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	@Column(name = "dir_delegacion")
	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	@Column(name = "dir_estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "dir_cp")
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "contacto")
	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	@Column(name = "edo_museo")
	public String getEdoMuseo() {
		return edoMuseo;
	}

	public void setEdoMuseo(String edoMuseo) {
		this.edoMuseo = edoMuseo;
	}

	@XmlTransient
	@ManyToMany
	@JoinTable(name = "MuseoTema", joinColumns = { @JoinColumn(name = "id_museo") }, inverseJoinColumns = { @JoinColumn(name = "id_tema") })
	public List<Tema> getTemas() {
		return temas;
	}

	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}

	@XmlTransient
	@OneToMany(mappedBy = "tema")
	public List<MuseoTema> getMuseoTemas() {
		return museoTemas;
	}

	public void setMuseoTemas(List<MuseoTema> museoTemas) {
		this.museoTemas = museoTemas;
	}

	@XmlTransient
	@OneToMany(mappedBy = "museo")
	public List<Exposicion> getExposiciones() {
		return exposiciones;
	}

	public void setExposiciones(List<Exposicion> exposiciones) {
		this.exposiciones = exposiciones;
	}

}
