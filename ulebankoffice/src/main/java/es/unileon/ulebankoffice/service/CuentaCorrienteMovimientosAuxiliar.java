/**
 * 
 */
package es.unileon.ulebankoffice.service;

import java.util.List;

import javax.validation.Valid;

/**
 * @author Razvan Raducu
 *
 */
public class CuentaCorrienteMovimientosAuxiliar {
	
	@Valid
	private List<MovimientoCuentaCorriente> movimientos;

	public List<MovimientoCuentaCorriente> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientoCuentaCorriente> movimientos) {
		this.movimientos = movimientos;
	}
	
	
	
}
