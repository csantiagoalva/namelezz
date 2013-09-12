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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mx.ipn.escom.ars.util.IntegerAdapter;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@XmlRootElement
@Entity
@Table(name = "Recompensa")
public class Recompensa {
	private Integer idRecompensa;
	private String nombre;
	private String url;
	private Integer idLogro;
	private String urlText;

	private Logro logro;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_recompensa")
	public Integer getIdRecompensa() {
		return idRecompensa;
	}

	public void setIdRecompensa(Integer idRecompensa) {
		this.idRecompensa = idRecompensa;
	}

	@Column(name = "nb_recompensa")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "url_recompensa")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "url_textura")
	public String getUrlText() {
		return urlText;
	}

	public void setUrlText(String urlText) {
		this.urlText = urlText;
	}
	
	@Column(name = "id_logro")
	public Integer getIdLogro() {
		return idLogro;
	}

	public void setIdLogro(Integer idLogro) {
		this.idLogro = idLogro;
	}

	@XmlTransient
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_logro", referencedColumnName = "id_logro", insertable = false, updatable = false) })
	public Logro getLogro() {
		return logro;
	}

	public void setLogro(Logro logro) {
		this.logro = logro;
	}


}