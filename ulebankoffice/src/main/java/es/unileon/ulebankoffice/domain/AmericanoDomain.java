package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class AmericanoDomain extends Prestamo {
	
	private double capInicial, tipoInteres;
	private int periodos, tipoPeriodo;
	
	public AmericanoDomain(double C0, double i, int k, int p) {
		this.capInicial = C0;
		this.tipoInteres = i / 100.0;
		this.periodos = k;
		this.tipoPeriodo = p;
	}

	public List<List<String>> calcularTabla() {
		int numFilas = periodos * tipoPeriodo + 1;
		
		double interesCons = capInicial * tipoInteres / tipoPeriodo;
		
		double[] anualidad = new double[numFilas];
		double[] interes = new double[numFilas];
		double[] amortizacion = new double[numFilas];
		double[] capitalPendiente = new double[numFilas];
		double totalAnualidad = 0;
		double totalInteres = 0;
		double totalAmortizacion = 0;
		
		anualidad[0] = 0;
		anualidad[numFilas-1] = capInicial + interesCons;
		
		interes[0] = 0;
		interes[numFilas-1] = interesCons;
		
		amortizacion[0] = 0;
		amortizacion[numFilas-1] = anualidad[numFilas-1] - interes[numFilas-1];
		
		capitalPendiente[0] = capInicial;
		
		for(int i=1; i<numFilas-1; i++) {
			interes[i] = interesCons;
			anualidad[i] = interes[i];
			amortizacion[i] = anualidad[i] - interes[i];
			capitalPendiente[i] = capInicial;
		}
		
		capitalPendiente[numFilas-1] = capitalPendiente[numFilas-2] - amortizacion[numFilas-1];
		
		List<List<String>> tabla = new ArrayList<List<String>>();
		
		List<String> itemTabla;
		for(int i=0; i<numFilas; i++) {
			itemTabla = new ArrayList<String>();
			
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
		List<String> header = new ArrayList<String>();
		
		header.add("Periodo");
		header.add("Anualidad");
		header.add("Interés");
		header.add("Amortización");
		header.add("Capital pendiente");
		
		return header;
	}
	
	public double getCapInicial() {
		return capInicial;
	}
	
	public void setCapInicial(double capInicial) {
		this.capInicial = capInicial;
	}
	
	public double getTipoInteres() {
		return tipoInteres;
	}
	
	public void setTipoInteres(double tipoInteres) {
		this.tipoInteres = tipoInteres / 100.0;
	}
	
	public int getPeriodos() {
		return periodos;
	}
	
	public void setPeriodos(int periodos) {
		this.periodos = periodos;
	}
	
	public int getTipoPeriodo() {
		return tipoPeriodo;
	}
	
	public void setTipoPeriodo(int tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}
}
