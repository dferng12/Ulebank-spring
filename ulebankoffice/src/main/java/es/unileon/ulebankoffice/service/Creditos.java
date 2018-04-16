package es.unileon.ulebankoffice.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * POJO plantilla para transmitir datos de la interfaz web a la instanciación
 * del objeto de dominio. Cuenta de crédito
 * 
 * @author Razvan Raducu, Alexis Gutierrez
 *
 */
public class Creditos {

	@NotNull
	@Min(1)
	private double limiteCredito;

	@NotNull
	private String fechaApertura;

	@NotNull
	private String fechaVencimiento;

	@NotNull
	@Min(0)
	private double comisionApertura;

	@NotNull
	@Min(0)
	private double corretaje;

	@NotNull
	@Min(0)
	private double interesDeudor;

	@NotNull
	@Min(0)
	private double interesExcedido;

	@NotNull
	@Min(0)
	private double interesAcreedor;

	@NotNull
	@Min(0)
	private double comisionSMND;

	private List<MovimientosCreditos> movimientos;

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public String getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public double getInteresDeudor() {
		return interesDeudor;
	}

	public void setInteresDeudor(double interesDeudor) {
		this.interesDeudor = interesDeudor;
	}

	public double getInteresExcedido() {
		return interesExcedido;
	}

	public void setInteresExcedido(double interesExcedido) {
		this.interesExcedido = interesExcedido;
	}

	public double getInteresAcreedor() {
		return interesAcreedor;
	}

	public void setInteresAcreedor(double interesAcreedor) {
		this.interesAcreedor = interesAcreedor;
	}

	public double getComisionApertura() {
		return comisionApertura;
	}

	public void setComisionApertura(double comisionApertura) {
		this.comisionApertura = comisionApertura;
	}

	public double getCorretaje() {
		return corretaje;
	}

	public void setCorretaje(double corretaje) {
		this.corretaje = corretaje;
	}

	public double getComisionSMND() {
		return comisionSMND;
	}

	public void setComisionSMND(double comisionSMND) {
		this.comisionSMND = comisionSMND;
	}

	public List<MovimientosCreditos> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientosCreditos> movimientos) {
		this.movimientos = movimientos;
	}
}
