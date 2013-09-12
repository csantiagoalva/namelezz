package mx.ipn.escom.ars.modelo;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "Recurso")
public class Recurso {
	private Integer idRecurso;	
	private Integer idElemento;	
	private String nombreArchivoFisico;
	private String url;
	
	private byte[] archivo;
	private Integer idTemporal;
	
	private Elemento elemento;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_recurso")
	public Integer getIdRecurso() {
		return idRecurso;
	}
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}
		
	
	@Column(name = "id_elemento")
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}

	@Column(name = "nb_archivo_fisico")
	public String getNombreArchivoFisico() {
		return nombreArchivoFisico;
	}
	public void setNombreArchivoFisico(String nombreArchivoFisico) {
		this.nombreArchivoFisico = nombreArchivoFisico;
	}
	@Column(name = "url_archivo")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		
	@XmlTransient
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_elemento", referencedColumnName = "id_elemento", insertable = false, updatable = false) })
	public Elemento getElemento() {
		return elemento;
	}
	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}
	
	
	@Transient
	public Integer getIdTemporal() {
		return idTemporal;
	}
	public void setIdTemporal(Integer idTemporal) {
		this.idTemporal = idTemporal;
	}
	@Transient
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	
	
	
	
	
	
	
	

		
}