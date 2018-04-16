/**
 * 
 */
package es.unileon.ulebankoffice.domain;

import java.util.Date;

/**
 * @author Razvan Raducu
 *
 */
public class MovimientoCuentaCorrienteDomain {

	private Double importe;
	private String concepto;
	private Date fechaValor;
	private String operacion;

	/**
	 * El constructor vacío es necesario para poder instanciar los
	 * modelAttributes
	 */
	public MovimientoCuentaCorrienteDomain() {
		/* Necesario para los model attributes */
	}

	/**
	 * Recibe la fecha en formato Date.
	 * 
	 * @param cantidad
	 * @param concepto
	 * @param fecha
	 * @param operacion
	 */
	public MovimientoCuentaCorrienteDomain(Double cantidad, String concepto, Date fecha, String operacion) {
		this.importe = cantidad;
		this.concepto = concepto;
		this.fechaValor = fecha;
		this.operacion = operacion;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Date getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(Date fecha) {
		this.fechaValor = fecha;
	}

}
