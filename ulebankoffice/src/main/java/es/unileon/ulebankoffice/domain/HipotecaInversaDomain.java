package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class HipotecaInversaDomain extends Operacion {
	
	private double valorTasacion; 
	private double porcentajeSobreTasacion; 
	private double tipoInteresPrestamo; 
	private double comisionApertura; 
	private double rentabilidadRenta; 
	private double costeTasacion; 
	private double gastosFormalizacion;
	
	private int edad;
	
	private static final int EDADMAX = 90;
	
	public HipotecaInversaDomain(double vT, int e, double pST, double tIP, double cA,
			double rR, double cT, double gF) {
		
		this.valorTasacion = vT;
		this.edad = e;
		this.porcentajeSobreTasacion = pST / 100.0;
		this.tipoInteresPrestamo = tIP / 100.0;
		this.comisionApertura = cA / 100.0;
		this.rentabilidadRenta = rR / 100.0;
		this.costeTasacion = cT;
		this.gastosFormalizacion = gF;
	}
	
	public List<List<String>> calcularTabla() {
		double valorPrestamo = valorTasacion * porcentajeSobreTasacion;
		double comiApertura = valorPrestamo * comisionApertura;
		
		double totalGastos = costeTasacion + comiApertura + gastosFormalizacion;
		
		double importeDisponible = valorPrestamo - totalGastos;
		
		int difEdad = EDADMAX - edad;
		
		double dividendo = rentabilidadRenta * Math.pow(1+rentabilidadRenta, difEdad) * ((-1)*importeDisponible);
		double divisor = Math.pow(1+rentabilidadRenta, difEdad) - 1;
		double anualidadRenta = (-1) * (dividendo / divisor);
		
		double interesesDelPrestamo = valorPrestamo * tipoInteresPrestamo;
		
		double importeMensual = anualidadRenta / 12;
		double interesesMensuales = interesesDelPrestamo / 12;
		
		double libreDisposicion = importeMensual - interesesMensuales;
		
		List<List<String>> tabla = new ArrayList<>();
		List<String> itemTabla = new ArrayList<>();
		
		itemTabla.add(Double.toString(redondear(valorPrestamo)));
		itemTabla.add(Double.toString(redondear(totalGastos)));
		itemTabla.add(Double.toString(redondear(importeMensual)));
		itemTabla.add(Double.toString(redondear(interesesMensuales)));
		itemTabla.add(Double.toString(redondear(libreDisposicion)));
		
		tabla.add(itemTabla);
		
		return tabla;
	}

	public double getValorTasacion() {
		return valorTasacion;
	}

	public void setValorTasacion(double valorTasacion) {
		this.valorTasacion = valorTasacion;
	}

	public double getPorcentajeSobreTasacion() {
		return porcentajeSobreTasacion;
	}

	public void setPorcentajeSobreTasacion(double porcentajeSobreTasacion) {
		this.porcentajeSobreTasacion = porcentajeSobreTasacion / 100.0;
	}

	public double getTipoInteresPrestamo() {
		return tipoInteresPrestamo;
	}

	public void setTipoInteresPrestamo(double tipoInteresPrestamo) {
		this.tipoInteresPrestamo = tipoInteresPrestamo / 100.0;
	}

	public double getComisionApertura() {
		return comisionApertura;
	}

	public void setComisionApertura(double comisionApertura) {
		this.comisionApertura = comisionApertura / 100.0;
	}

	public double getRentabilidadRenta() {
		return rentabilidadRenta;
	}

	public void setRentabilidadRenta(double rentabilidadRenta) {
		this.rentabilidadRenta = rentabilidadRenta / 100.0;
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
}
