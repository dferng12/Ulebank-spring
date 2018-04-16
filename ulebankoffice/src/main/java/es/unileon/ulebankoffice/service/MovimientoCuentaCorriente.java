/**
 * 
 */
package es.unileon.ulebankoffice.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Razvan Raducu
 *
 */
public class MovimientoCuentaCorriente {

    @NotNull @Min(0)
	private Double importe;
	@NotBlank
	private String concepto;
	@NotBlank
	private String fechaValor;
	@NotBlank
	private String operacion;

	public Double getImporte() {
		return importe;
	}
	
	public String getConcepto(){
		return concepto;
	}
	
	public void setImporte(Double cantidad) {
		this.importe = cantidad;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(String date) {
		this.fechaValor = date;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
	
	
}
