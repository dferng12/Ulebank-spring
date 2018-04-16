package es.unileon.ulebankoffice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase que abstrae el comportamiento común a los distintos movimientos que se
 * pueden realizar en los distintos productos finacnieros.
 * 
 * @author Razvan Raducu, Alexis Gutierrez
 *
 */
public abstract class Operacion {

	/**
	 * Returns the double value that is closest in value to the argument and is
	 * equal to a mathematical integer.
	 * 
	 * <b>>Edit</b 10.05.2017 (dd-MM-yy). He cambiado lo que reotnra la función
	 * para que redondee siempre a las 2 cifras. Se debe usar BigDecimal ya que
	 * tanto double como float son inexactos al tratarse de muchos decimales.
	 * 
	 * 
	 * @param r
	 * @return El argumento double redondeado
	 */
	public double redondear(double r) {
		return BigDecimal.valueOf(Math.rint(r * 100) / 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

}
