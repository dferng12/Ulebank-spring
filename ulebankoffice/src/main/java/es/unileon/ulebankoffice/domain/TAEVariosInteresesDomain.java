package es.unileon.ulebankoffice.domain;

import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.formula.functions.Irr;

import es.unileon.ulebankoffice.service.InteresVariable;

/**
 * @author Razvan Raducu
 *
 */
public class TAEVariosInteresesDomain {

	private double cantidad;
	private int periodo;
	private List<InteresVariable> intereses;

	/**
	 * Para calcular el tae en función de varios intereses distintos es
	 * necesario especificar todos esos intereses y el numero de preiodos que
	 * hay. El número de intereses debe coincidir con el número de periodos. En
	 * caso de no hacerlo se recorrerá la lsita de intereses desde index = 0
	 * hasta index < periodo
	 * 
	 * @param periodo
	 * @param intereses
	 */
	public TAEVariosInteresesDomain(int periodo, List<InteresVariable> intereses) {
		// La cantidad no es necesaria. La TAE es independiente de la cantidad,
		// se utiliza sólo para poder obtener la solución.
		this.cantidad = 100;
		this.periodo = periodo;
		this.intereses = intereses;
	}

	
	/**
	 * Realizar el cálculo del tae teniendo en cuenta el periodo e intereses especificados a través del el cosntrucor
	 * 
	 * @return
	 */
	public String calcular() {
		
		new Irr();
		double irr;
		double tae;
		
		// Hay que sumar 1 puesto que la cantidad ha de introducirse en los
		// flujos
		double[] flujos = new double[periodo + 1];

		flujos[0] = -cantidad;
		for (int i = 0; i < periodo; i++) {

			flujos[i + 1] = (intereses.get(i).getInteres() / (100)) * cantidad / periodo;

		}

		// Hay que sumar de nuevo la cantidad inicial
		flujos[flujos.length - 1] += cantidad;

		
		irr = Irr.irr(flujos, 0.1d);

		tae = (Math.pow(1.0 + irr, periodo) - 1.0) * 100;

		// Después de hacer todas las operaciones y antes de añadir a la tabla,
		// redondeo sus valores.

		// En las últimas dos posiciones de la tabla se encuentra el IRR y el
		// TAE, resultado final. Estas posiciones se borrarán para imprimir la
		// tabla en la vista con un foreach, de esto se encarga el controlador.

		// Después de hacer todas las operaciones y antes de añadir a la tabla,
		// redondeo sus valores.
		return new BigDecimal(Double.toString(tae)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public List<InteresVariable> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<InteresVariable> intereses) {
		this.intereses = intereses;
	}

	@Override
	public String toString() {
		return "TAEVariosInteresesDomain [cantidad=" + cantidad + ", periodo=" + periodo + ", intereses=" + intereses
				+ "]";
	}

}
