package es.unileon.ulebankoffice.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Descuentos {
	
	@NotNull @Min(1)
	private double importeDescuento;
	
	@NotNull @Min(1)
	private int periodoDescuento;
	
	@NotNull @Min(0)
	private double tipoInteres;
	
	private int base;
	
	@NotNull @Min(0)
	private double otrosGastos;
	
	@NotNull @Min(0)
	private double comisiones;

	public double getImporteDescuento() {
		return importeDescuento;
	}

	public void setImporteDescuento(double importeDescuento) {
		this.importeDescuento = importeDescuento;
	}

	public int getPeriodoDescuento() {
		return periodoDescuento;
	}

	public void setPeriodoDescuento(int periodoDescuento) {
		this.periodoDescuento = periodoDescuento;
	}

	public double getTipoInteres() {
		return tipoInteres;
	}

	public void setTipoInteres(double tipoInteres) {
		this.tipoInteres = tipoInteres;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public double getOtrosGastos() {
		return otrosGastos;
	}

	public void setOtrosGastos(double otrosGastos) {
		this.otrosGastos = otrosGastos;
	}

	public double getComisiones() {
		return comisiones;
	}

	public void setComisiones(double comisiones) {
		this.comisiones = comisiones;
	}
}
