package es.unileon.ulebankoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class ItalianoDomain extends Prestamo {
	
	private double capInicial;
	private double tipoInteres;
	private int periodos;
	private int tipoPeriodo;
	
	public ItalianoDomain(double C0, double i, int k, int p) {
		this.capInicial = C0;
		this.tipoInteres = i / 100.0;
		this.periodos = k;
		this.tipoPeriodo = p;
	}
	
	public List<List<String>> calcularTabla() {
		double amortizacionCons = capInicial / (periodos * tipoPeriodo);
		
		int numFilas = (periodos * tipoPeriodo) + 1;
		
		double[] capPendiente = new double[numFilas];
		double[] columnaInteres = new double[numFilas];
		double[] anualidad = new double[numFilas];
		double totalAnualidad = 0;
		double totalInteres = 0;
		double totalAmortizacion = 0;
		
		capPendiente[0] = capInicial;
		
		for(int i=1; i<capPendiente.length; i++)
			capPendiente[i] = capPendiente[i-1] - amortizacionCons;
		
		for(int i=1; i<columnaInteres.length; i++)
			columnaInteres[i] = capPendiente[i-1] * tipoInteres / tipoPeriodo;
		
		for(int i=1; i<anualidad.length; i++)
			anualidad[i] = columnaInteres[i] + amortizacionCons;
		
		List<List<String>> tabla = new ArrayList<>();
		
		List<String> itemTabla;
		for(int i=0; i<numFilas; i++) {
			itemTabla = new ArrayList<>();
			
			if(i == 0) {
				itemTabla.add(Integer.toString(i));
				itemTabla.add("0");
				itemTabla.add("0");
				itemTabla.add("0");
				itemTabla.add(Double.toString(redondear(capPendiente[i])));
			}
			else {
				itemTabla.add(Integer.toString(i));
				itemTabla.add(Double.toString(redondear(anualidad[i])));
				itemTabla.add(Double.toString(redondear(columnaInteres[i])));
				itemTabla.add(Double.toString(redondear(amortizacionCons)));
				itemTabla.add(Double.toString(redondear(capPendiente[i])));
				
				totalAnualidad += anualidad[i];
				totalInteres += columnaInteres[i];
				totalAmortizacion += amortizacionCons;
			}
			
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
