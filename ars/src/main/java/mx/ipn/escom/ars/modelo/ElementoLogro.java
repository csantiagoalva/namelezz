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
@Table(name = "ElementoLogro")
public class ElementoLogro {
	private Integer idElemento;
	private Integer idLogro;

	private Logro logro;
	private Elemento elemento;
	private ElementoLogroId elementoLogroId;

	public ElementoLogro() {
	}

	public ElementoLogro(Integer idElemento, Integer idLogro) {
		this.elementoLogroId = new ElementoLogroId();
		this.elementoLogroId.setIdElemento(idElemento);
		this.elementoLogroId.setIdLogro(idLogro);
	}

	public ElementoLogro(ElementoLogroId elementoLogroId) {
		this.elementoLogroId = elementoLogroId;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "expo", "logro");
	}

	@Column(name = "id_Elemento", insertable = false, updatable = false)
	public Integer getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}

	@Column(name = "id_logro", insertable = false, updatable = false)
	public Integer getIdLogro() {
		return idLogro;
	}

	public void setIdLogro(Integer idlogro) {
		this.idLogro = idlogro;
	}

	@EmbeddedId
	public ElementoLogroId getElementoLogroId() {
		return elementoLogroId;
	}

	public void setElementoLogroId(ElementoLogroId elementoLogroId) {
		this.elementoLogroId = elementoLogroId;
	}

	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "id_elemento", referencedColumnName = "id_elemento", insertable = false, updatable = false)
	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "id_logro", referencedColumnName = "id_logro", insertable = false, updatable = false)
	public Logro getLogro() {
		return logro;
	}

	public void setLogro(Logro logro) {
		this.logro = logro;
	}
}
