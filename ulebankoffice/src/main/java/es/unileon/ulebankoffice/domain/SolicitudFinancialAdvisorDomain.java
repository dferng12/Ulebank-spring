/**
 * 
 */
package es.unileon.ulebankoffice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Razvan Raducu
 *
 */
@Document(collection = "SolicitudesFinancialAdvisor")
public class SolicitudFinancialAdvisorDomain {

	@Id
	private String id;
	private String email;
	private String textoOferta;
	private String urlOferta;
	private String estado;
	private String fileBlobKey;
	private String asuntoOferta;
	private String respuestaOferta;
	/*
	 * ATENCIÓN!!! Guardar al fecha en formato String es una mala práctica y
	 * supone un mal diseño. La persona que lea esto debería encargarse de
	 * cambiar. Yo lo hice así porque no he tenido más tiempo. Este mensaje fue
	 * escrito el 01.06.2017
	 */
	private String fechaCreacion;

	public String getTextoOferta() {
		return textoOferta;
	}

	public void setTextoOferta(String textoOferta) {
		this.textoOferta = textoOferta;
	}

	public String getUrlOferta() {
		return urlOferta;
	}

	public void setUrlOferta(String rutaOferta) {
		this.urlOferta = rutaOferta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileBlobKey() {
		return fileBlobKey;
	}

	public void setFileBlobKey(String fileBlobKey) {
		this.fileBlobKey = fileBlobKey;
	}

	public String getId() {
		return id;
	}

	public String getAsuntoOferta() {
		return asuntoOferta;
	}

	public void setAsuntoOferta(String asuntoOferta) {
		this.asuntoOferta = asuntoOferta;
	}

	public String getRespuestaOferta() {
		return respuestaOferta;
	}

	public void setRespuestaOferta(String respuestaOferta) {
		this.respuestaOferta = respuestaOferta;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
