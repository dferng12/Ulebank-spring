package es.unileon.ulebankoffice.service;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * POJO auxiliar para transmitir la información de los movimientos de la cueta
 * de crédito.
 * 
 * @author Razvan Raducu
 *
 */
public class MovimientosCreditos {

	@NotBlank
	private String descripcionMovimiento;

	@Min(0)
	private double importeMovimiento;

	@NotBlank
	private String fechaMovimiento;

	// I -> Ingreso. D -> Disposicion
	private String operacion;

	public String getDescripcionMovimiento() {
		return descripcionMovimiento;
	}

	public void setDescripcionMovimiento(String descripcionMovimiento) {
		this.descripcionMovimiento = descripcionMovimiento;
	}

	public double getImporteMovimiento() {
		return importeMovimiento;
	}

	public void setImporteMovimiento(double importeMovimiento) {
		this.importeMovimiento = importeMovimiento;
	}

	public String getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(String fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

}
