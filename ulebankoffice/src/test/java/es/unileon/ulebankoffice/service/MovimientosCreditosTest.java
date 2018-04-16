/**
 * 
 */
package es.unileon.ulebankoffice.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Razvan Raducu
 *
 */
public class MovimientosCreditosTest {

	private static Validator validator;
	private MovimientosCreditos movimiento;
	private Set<ConstraintViolation<MovimientosCreditos>> constraintViolations;
	
	@BeforeClass
	   public static void setUpClass() {
	      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	      validator = factory.getValidator();
	   }
	
	@Before
	public void setUp() throws Exception {
		movimiento = new MovimientosCreditos();
		movimiento.setDescripcionMovimiento("Movimiento test");
		movimiento.setFechaMovimiento("1994-12-05");
		movimiento.setImporteMovimiento(150000d);
		movimiento.setOperacion("I");
	}


	@Test
	public void testGetDescripcionMovimiento() {
		assertThat(movimiento.getDescripcionMovimiento(), is("Movimiento test"));
	}

	@Test
	public void testGetImporteMovimiento() {
		assertThat(movimiento.getImporteMovimiento(), is(150000.0));
	}

	@Test
	public void testGetFechaMovimiento() {
		assertThat(movimiento.getFechaMovimiento(), is("1994-12-05"));
	}

	@Test
	public void testGetOperacion() {
		assertThat(movimiento.getOperacion(), is("I"));
	}
	
	@Test
	public void testAnnotationDescription(){
		validate();
		assertThat(constraintViolations.size(), is(0));
		movimiento.setDescripcionMovimiento("");
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("descripcionMovimiento"));
	}
	
	@Test
	public void testAnnotationImporte(){
		validate();
		assertThat(constraintViolations.size(), is(0));
		movimiento.setImporteMovimiento(-150d);
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("importeMovimiento"));
	}
	
	@Test
	public void testAnnotatioFechan(){
		validate();
		assertThat(constraintViolations.size(), is(0));
		movimiento.setFechaMovimiento("");
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("fechaMovimiento"));
	}
	
	
	private void validate(){
		constraintViolations = validator.validate(movimiento);
	}
	
}
