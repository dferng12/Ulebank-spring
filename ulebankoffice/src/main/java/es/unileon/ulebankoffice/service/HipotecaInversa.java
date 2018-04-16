package es.unileon.ulebankoffice.service;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class HipotecaInversa {
	
	@NotNull @Min(1)
	private double valorTasacion;
	
	@NotNull @Min(65) @Max(89)
	private int edad;
	
	@NotNull @Min(0)
	private double porcentajeSobreTasacion;
	
	@NotNull @Min(0)
	private double tipoInteresPrestamo;
	
	@NotNull @Min(0)
	private double comisionApertura;
	
	@NotNull @Min(0)
	private double rentabilidadRenta;
	
	@NotNull @Min(0)
	private double costeTasacion;
	
	@NotNull @Min(0)
	private double gastosFormalizacion;

	public double getValorTasacion() {
		return valorTasacion;
	}

	public void setValorTasacion(double valorTasacion) {
		this.valorTasacion = valorTasacion;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getPorcentajeSobreTasacion() {
		return porcentajeSobreTasacion;
	}

	public void setPorcentajeSobreTasacion(double porcentajeSobreTasacion) {
		this.porcentajeSobreTasacion = porcentajeSobreTasacion;
	}

	public double getTipoInteresPrestamo() {
		return tipoInteresPrestamo;
	}

	public void setTipoInteresPrestamo(double tipoInteresPrestamo) {
		this.tipoInteresPrestamo = tipoInteresPrestamo;
	}

	public double getComisionApertura() {
		return comisionApertura;
	}

	public void setComisionApertura(double comisionApertura) {
		this.comisionApertura = comisionApertura;
	}

	public double getRentabilidadRenta() {
		return rentabilidadRenta;
	}

	public void setRentabilidadRenta(double rentabilidadRenta) {
		this.rentabilidadRenta = rentabilidadRenta;
	}

	public double getCosteTasacion() {
		return costeTasacion;
	}

	public void setCosteTasacion(double costeTasacion) {
		this.costeTasacion = costeTasacion;
	}

	public double getGastosFormalizacion() {
		return gastosFormalizacion;
	}

	public void setGastosFormalizacion(double gastosFormalizacion) {
		this.gastosFormalizacion = gastosFormalizacion;
	}
}
