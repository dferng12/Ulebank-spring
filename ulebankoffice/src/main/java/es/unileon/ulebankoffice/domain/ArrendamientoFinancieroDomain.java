package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class ArrendamientoFinancieroDomain extends Operacion {
	
	private double valorBien, tipoInteres;
	private int duracionContrato, fraccionamientoPagoCuota;
	
	private final double IVA = 0.21;
	
	public ArrendamientoFinancieroDomain(double valorBien, int duracionContrato, int fraccionamientoPagoCuota, double tipoInteres) {
		this.valorBien = valorBien;
		this.duracionContrato = duracionContrato;
		this.fraccionamientoPagoCuota = fraccionamientoPagoCuota;
		this.tipoInteres = tipoInteres / 100.0;
	}
	
	public List<List<String>> calcularTabla() {
		double aux = 1 + (tipoInteres / fraccionamientoPagoCuota);
		double arriba = Math.pow(aux, (duracionContrato*fraccionamientoPagoCuota)) * (tipoInteres / fraccionamientoPagoCuota);
		double abajo = Math.pow(aux, ((duracionContrato*fraccionamientoPagoCuota)+1)) - 1;
		
		double cuota = valorBien * (arriba / abajo);
		
		int totalPeriodos = duracionContrato * fraccionamientoPagoCuota + 1;
		
		double[] capitalPendiente = new double[totalPeriodos];
		double[] intereses = new double[totalPeriodos];
		double[] amortizacion = new double[totalPeriodos];
		double[] totalAmortizado = new double[totalPeriodos];
		
		List<List<String>> tabla = new ArrayList<List<String>>();
		
		List<String> itemTabla;
		for(int i=0; i<totalPeriodos; i++) {
			itemTabla = new ArrayList<String>();
			
			if(i == 0) {
				intereses[i] = 0;
				amortizacion[i] = cuota - intereses[i];
				totalAmortizado[i] = amortizacion[i];
				capitalPendiente[i] = valorBien - amortizacion[i];
			}
			else {
				intereses[i] = capitalPendiente[i-1] * tipoInteres / fraccionamientoPagoCuota;
				amortizacion[i] = cuota - intereses[i];
				totalAmortizado[i] = totalAmortizado[i-1] + amortizacion[i];
				capitalPendiente[i] = valorBien - totalAmortizado[i];
			}
			
			itemTabla.add(Integer.toString(i));
			itemTabla.add(Double.toString(redondear(cuota)));
			itemTabla.add(Double.toString(redondear(intereses[i])));
			itemTabla.add(Double.toString(redondear(amortizacion[i])));
			itemTabla.add(Double.toString(redondear(capitalPendiente[i])));
			
			tabla.add(itemTabla);
		}
		
		return tabla;
	}

	public double getValorBien() {
		return valorBien;
	}

	public void setValorBien(double valorBien) {
		this.valorBien = valorBien;
	}

	public double getTipoInteres() {
		return tipoInteres;
	}

	public void setTipoInteres(double tipoInteres) {
		this.tipoInteres = tipoInteres / 100.0;
	}

	public int getDuracionContrato() {
		return duracionContrato;
	}

	public void setDuracionContrato(int duracionContrato) {
		this.duracionContrato = duracionContrato;
	}

	public int getFraccionamientoPagoCuota() {
		return fraccionamientoPagoCuota;
	}

	public void setFraccionamientoPagoCuota(int fraccionamientoPagoCuota) {
		this.fraccionamientoPagoCuota = fraccionamientoPagoCuota;
	}
}
