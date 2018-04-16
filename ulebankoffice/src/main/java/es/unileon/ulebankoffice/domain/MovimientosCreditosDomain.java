package es.unileon.ulebankoffice.domain;

import java.util.Date;

/**
 * Clare que representa la lógica de negocio de los movimientos asociados a la
 * cuenta de crédito.
 * 
 * @author Razvan Raducu, Alexis Gutierrez
 *
 */
public class MovimientosCreditosDomain {

	private String descripcionMovimiento;
	private double importeMovimiento;
	private Date fechaMovimiento;
	private String operacion;

	private boolean procesado = false;

	/**
	 * @param descripcionMovimiento
	 * @param importeMovimiento
	 * @param fechaMovimiento
	 * @param operacion
	 */
	public MovimientosCreditosDomain(String descripcionMovimiento, double importeMovimiento, Date fechaMovimiento,
			String operacion) {
		this.descripcionMovimiento = descripcionMovimiento;
		this.importeMovimiento = importeMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.operacion = operacion;
	}

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

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public boolean isProcesado() {
		return procesado;
	}

	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}

	/**
	 * Devuelve el signo de la operació del movimiento
	 * 
	 * @return D - Disposición, I - Ingreso
	 */
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

}
