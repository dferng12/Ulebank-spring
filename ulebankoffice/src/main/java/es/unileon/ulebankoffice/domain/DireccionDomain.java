/**
 * 
 */
package es.unileon.ulebankoffice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Razvan Raducu
 *
 */
@Document(collection = "Direcciones")
public class DireccionDomain {

	@Id
	private String id;

	private String calle;
	private String localidad;
	private String comunidadAutonoma;
	private String numero;

	private Handler dni;

	private int codigoPostal;

	/**
	 * Constructor utilizado para instanciar objetos manualmente pasando el DNI
	 * como una string.
	 * 
	 * @param dni
	 * @param calle
	 * @param localidad
	 * @param comunidadAutonoma
	 * @param codigoPostal
	 * @param numero
	 * @throws DNIException
	 */
	public DireccionDomain(String dni, String calle, String localidad, String comunidadAutonoma, int codigoPostal,
			String numero) throws DNIException {

		setDni(dni);
		this.calle = calle;
		this.localidad = localidad;
		this.comunidadAutonoma = comunidadAutonoma;
		this.codigoPostal = codigoPostal;
		this.numero = numero;

	}

	/**
	 * Persistence constructor usado para instanciar objetos a partir de la base
	 * de datos.
	 * 
	 * @param calle
	 * @param localidad
	 * @param comunidadAutonoma
	 * @param numero
	 * @param dni
	 * @param codigoPostal
	 */
	@PersistenceConstructor
	public DireccionDomain(String calle, String localidad, String comunidadAutonoma, String numero, Handler dni,
			int codigoPostal) {
		super();
		this.calle = calle;
		this.localidad = localidad;
		this.comunidadAutonoma = comunidadAutonoma;
		this.numero = numero;
		this.dni = dni;
		this.codigoPostal = codigoPostal;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Handler getDni() {
		return dni;
	}

	public void setDni(Handler dni) {
		this.dni = dni;
	}

	public void setDni(String dni) throws DNIException {
		this.dni = new DNIHandler(dni);
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "DireccionDomain [id=" + id + ", calle=" + calle + ", localidad=" + localidad + ", comunidadAutonoma="
				+ comunidadAutonoma + ", numero=" + numero + ", dni=" + dni + ", codigoPostal=" + codigoPostal + "]";
	}

}
