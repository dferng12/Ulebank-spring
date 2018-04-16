package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class FrancesDomain extends Prestamo {
	
	private double tipoInteres;
	private double capInicial;
	private int periodos;
	private int tipoPeriodo;
	
	public FrancesDomain(double i, double C0, int k, int p) {
		this.tipoInteres = i / 100.0;
		this.capInicial = C0;
		this.periodos = k;
		this.tipoPeriodo = p;
	}
	
	public List<List<String>> calcularTabla() {
		int numFilas = periodos * tipoPeriodo + 1;
		
		double[] anualidad = new double[numFilas];
		double[] interes = new double[numFilas];
		double[] amortizacion = new double[numFilas];
		double[] capitalPendiente = new double[numFilas];
		double totalAnualidad = 0;
		double totalInteres = 0;
		double totalAmortizacion = 0;
		
		
		double Ani = (1 - Math.pow(1+tipoInteres/tipoPeriodo, periodos*(-1)* (double)tipoPeriodo)) / (tipoInteres/tipoPeriodo);
		double anualidadCons = capInicial / Ani;
		
		anualidad[0] = 0;
		interes[0] = 0;
		amortizacion[0] = 0;
		capitalPendiente[0] = capInicial;
		
		List<List<String>> tabla = new ArrayList<>();
		
		for(int i=1; i<numFilas; i++) {
			anualidad[i] = anualidadCons;
			interes[i] = capitalPendiente[i-1] * tipoInteres / tipoPeriodo;
			amortizacion[i] = anualidad[i] - interes[i];
			capitalPendiente[i] = capitalPendiente[i-1] - amortizacion[i];
		}
		
		List<String> itemTabla;
		for(int i=0; i<numFilas; i++) {
			itemTabla = new ArrayList<>();
			
			itemTabla.add(Integer.toString(i));
			itemTabla.add(Double.toString(redondear(anualidad[i])));
			itemTabla.add(Double.toString(redondear(interes[i])));
			itemTabla.add(Double.toString(redondear(amortizacion[i])));
			itemTabla.add(Double.toString(redondear(capitalPendiente[i])));
			
			totalAnualidad += anualidad[i];
			totalInteres += interes[i];
			totalAmortizacion += amortizacion[i];
			
			tabla.add(itemTabla);
		}
		
		itemTabla = new ArrayList<>();
		itemTabla.add(Double.toString(redondear(totalAnualidad)));
		itemTabla.add(Double.toString(redondear(totalInteres)));
		itemTabla.add(Double.toString(redondear(totalAmortizacion)));
		
		tabla.add(itemTabla);
		
		return tabla;
	}
	
	public List<String> getTableHeader() {
		List<String> header = new ArrayList<>();
		
		header.add("Periodo");
		header.add("Anualidad");
		header.add("Interés");
		header.add("Amortización");
		header.add("Capital pendiente");
		
		return header;
	}
	
	@Override
	public double getTipoInteres() {
		return tipoInteres;
	}
	
	@Override
	public void setTipoInteres(double tipoInteres) {
		this.tipoInteres = tipoInteres / 100.0;
	}
	
	@Override
	public double getCapInicial() {
		return capInicial;
	}
	
	@Override
	public void setCapInicial(double capInicial) {
		this.capInicial = capInicial;
	}
	
	@Override
	public int getPeriodos() {
		return periodos;
	}
	
	@Override
	public void setPeriodos(int periodos) {
		this.periodos = periodos;
	}
	
	@Override
	public int getTipoPeriodo() {
		return tipoPeriodo;
	}
	
	@Override
	public void setTipoPeriodo(int tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}
}
