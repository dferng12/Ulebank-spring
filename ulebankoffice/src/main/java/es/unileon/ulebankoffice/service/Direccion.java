package es.unileon.ulebankoffice.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Razvan Raducu
 *
 */
public class Direccion {

	@NotBlank
	private String calle;
	
	@NotBlank
	private String numero;
	
	/* NotEmpry es sólo para string, collections, maps o arrays */
	@NotNull @Min(0)
	private int codigoPostal;
	
	@NotBlank
	private String localidad;
	
	@NotBlank
	private String comunidadAutonoma;
	
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getComunidadAutonoma() {
		return comunidadAutonoma;
	}

	public void setComunidadAutonoma(String comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}

	@Override
	public String toString() {
		return "Direccion [calle=" + calle + ", numero=" + numero + ", codigoPostal=" + codigoPostal
				+ ", localidad=" + localidad + ", comunidadAutonoma=" + comunidadAutonoma + "]";
	}	
	
}
