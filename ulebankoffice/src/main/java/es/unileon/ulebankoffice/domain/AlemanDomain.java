package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase encargada de realizar la lógica de negocio relacionada con el préstamo método alemán
 * 
 * @author Razvan Raducu, Alexis Gutierrez
 *
 */
public class AlemanDomain extends Prestamo {
	
	private double capInicial;
	private double tipoInteres;
	private int periodos;
	private int tipoPeriodo;
	
	/**
	 * 
	 * @param C0
	 * @param i
	 * @param k
	 * @param p
	 */
	public AlemanDomain(double C0, double i, int k, int p) {
		this.capInicial = C0;
		this.tipoInteres = i / 100.0;
		this.periodos = k;
		this.tipoPeriodo = p;
	}
	
	public List<List<String>> calcularTabla() {
		int numFilas = (periodos * tipoPeriodo) + 1;
		
		double[] anualidad = new double[numFilas];
		double[] amortizacion = new double[numFilas];
		double[] interes = new double[numFilas];
		double[] capitalPendiente = new double[numFilas];
		double totalAnualidad = 0;
		double totalInteres = 0;
		double totalAmortizacion = 0;
		
		interes[0] = capInicial * tipoInteres / tipoPeriodo;
		
		anualidad[0] = interes[0];
		
		double anualidadCons = capInicial * (tipoInteres/tipoPeriodo) / (1-(Math.pow(1-(tipoInteres/tipoPeriodo), (double)periodos*tipoPeriodo)));
		
		for(int i=1; i<anualidad.length; i++)
			anualidad[i] = anualidadCons;
		
		amortizacion[numFilas-1] = anualidadCons;
		amortizacion[0] = 0;
		
		for(int i=amortizacion.length-2; i>0; i--)
			amortizacion[i] = amortizacion[i+1] * (1 - (tipoInteres/tipoPeriodo));
		
		for(int i=1; i<interes.length; i++)
			interes[i] = anualidad[i] - amortizacion[i];
		
		capitalPendiente[0] = capInicial;
		
		for(int i=1; i<capitalPendiente.length; i++)
			capitalPendiente[i] = capitalPendiente[i-1] - amortizacion[i];
		
		List<List<String>> tabla = new ArrayList<>();
		
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
	public double getCapInicial() {
		return capInicial;
	}
	@Override
	public void setCapInicial(double capInicial) {
		this.capInicial = capInicial;
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
