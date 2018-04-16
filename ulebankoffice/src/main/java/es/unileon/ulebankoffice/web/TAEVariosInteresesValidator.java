/**
 * 
 */
package es.unileon.ulebankoffice.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.unileon.ulebankoffice.service.InteresVariable;
import es.unileon.ulebankoffice.service.TAEVariosIntereses;

/**
 * @author Razvan Raducu
 *
 */
public class TAEVariosInteresesValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TAEVariosIntereses.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		int i = 0;
		TAEVariosIntereses tae = (TAEVariosIntereses) target;

		for (InteresVariable interes : tae.getIntereses()) {
			double interesAux = interes.getInteres();
			if (interesAux < 0) {
				// Esto crea dependencia entre la vista y el controlador/modelo
				// de negocio.
				// Si cambio el nombre del campo donde se muestra el error de
				// intereses[0].interes
				// a interest[0].interes, ¿Qué pasa?. Que hay un acoplamiento
				// innecesario.
				errors.rejectValue("intereses[" + i + "].interes", "fieldIsLessThanZero");
			}
			i++;
		}
	}
}
