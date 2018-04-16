package es.unileon.ulebankoffice.service;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Razvan Raducu
 *
 */

public class Cliente {

	@NotBlank
	private String name; 
	@NotBlank
	private String lastName; 
	@NotBlank
	private String email;

	@NotBlank
	private String fechaNacimiento;

	@NotBlank
	private String dni;
	
	@NotBlank
	private String nacionalidad;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	@Override
	public String toString() {
		return "Cliente [name=" + name + ", lastname=" + lastName + ", email=" + email
				+ ", fechaNacimiento=" + fechaNacimiento + ", dni=" + dni + "]";
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

}
