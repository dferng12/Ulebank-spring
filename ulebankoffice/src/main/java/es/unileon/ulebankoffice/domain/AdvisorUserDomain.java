/**
 * 
 */
package es.unileon.ulebankoffice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Razvan Raducu
 *
 */
@Document(collection = "AdvisorUsers")
public class AdvisorUserDomain {

	@Id
	private String id;
	private String email;
	/* Primitivo, por defecto se inicializa a false */
	private boolean realizadoTest;

	/* Primitivo, por defecto se inicializa a 0.0 */
	private double resultadoTest;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isRealizadoTest() {
		return realizadoTest;
	}

	public void setRealizadoTest(boolean realizadoTest) {
		this.realizadoTest = realizadoTest;
	}

	public double getResultadoTest() {
		return resultadoTest;
	}

	public void setResultadoTest(double resultadoTest) {
		this.resultadoTest = resultadoTest;
	}

	public String getId() {
		return id;
	}

}
