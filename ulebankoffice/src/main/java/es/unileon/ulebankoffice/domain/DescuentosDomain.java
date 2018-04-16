package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class DescuentosDomain extends Operacion {
	
	private double importeDescuento;
	private double tipoInteres;
	private double otrosGastos;
	private double comisiones;
	private int periodoDescuento;
	private int base;
	
	public DescuentosDomain(double importeDescuento, int periodoDescuento, double tipoInteres, int base, double otrosGastos, double comisiones) {
		this.importeDescuento = importeDescuento;
		this.periodoDescuento = periodoDescuento;
		this.tipoInteres = tipoInteres / 100.0;
		this.base = base;
		this.otrosGastos = otrosGastos;
		this.comisiones = comisiones / 100.0;
	}
	
	public List<List<String>> calcularTabla() {
		double intereses = (importeDescuento * periodoDescuento * tipoInteres) / base;
		double importeComisiones = comisiones * importeDescuento;
		double totalGastos = intereses + importeComisiones + otrosGastos;
		
		double efectivo = importeDescuento - totalGastos;
		
		List<List<String>> tabla = new ArrayList<>();
		List<String> itemTabla = new ArrayList<>();
		
		itemTabla.add(Double.toString(redondear(intereses)));
		itemTabla.add(Double.toString(redondear(importeComisiones)));
		itemTabla.add(Double.toString(redondear(totalGastos)));
		itemTabla.add(Double.toString(redondear(efectivo)));
		
		tabla.add(itemTabla);
		
		return tabla;
	}

	public double getImporteDescuento() {
		return importeDescuento;
	}

	public void setImporteDescuento(double importeDescuento) {
		this.importeDescuento = importeDescuento;
	}

	public double getTipoInteres() {
		return tipoInteres;
	}

	public void setTipoInteres(double tipoInteres) {
		this.tipoInteres = tipoInteres / 100.0;
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
		this.comisiones = comisiones / 1000.0;
	}

	public int getPeriodoDescuento() {
		return periodoDescuento;
	}

	public void setPeriodoDescuento(int periodoDescuento) {
		this.periodoDescuento = periodoDescuento;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}
}
