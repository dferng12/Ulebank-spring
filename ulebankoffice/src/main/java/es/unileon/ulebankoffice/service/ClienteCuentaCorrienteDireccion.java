package es.unileon.ulebankoffice.service;

import javax.validation.Valid;

/**
 * @author Razvan Raducu
 *
 */
public class ClienteCuentaCorrienteDireccion {
	
	@Valid
	private Cliente cliente;
	
	@Valid
	private Direccion direccion;
	
	@Valid
	private CuentaCorriente cuentaCorriente;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}
	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
	
	

}
