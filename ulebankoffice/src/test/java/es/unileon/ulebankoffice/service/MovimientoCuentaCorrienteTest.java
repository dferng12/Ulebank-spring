package es.unileon.ulebankoffice.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MovimientoCuentaCorrienteTest {

	private static Validator validator;
	private MovimientoCuentaCorriente movimiento;
	private Set<ConstraintViolation<MovimientoCuentaCorriente>> constraintViolations;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void setUpBeforeEach() {
		movimiento = new MovimientoCuentaCorriente();
	}

	@Test
	public void testCantidad() {
		assertNull(movimiento.getImporte());
		movimiento.setImporte(150d);
		assertThat(movimiento.getImporte(), is(150d));
	}

	@Test
	public void testConcepto() {
		assertNull(movimiento.getConcepto());
		movimiento.setConcepto("concepto");
		assertThat(movimiento.getConcepto(), is("concepto"));
	}

	@Test
	public void testDate() {
		assertNull(movimiento.getFechaValor());
		movimiento.setFechaValor("1994-12-05");
		assertThat(movimiento.getFechaValor(), is("1994-12-05"));
	}
	
	@Test
	public void testOperacion(){
		assertNull(movimiento.getOperacion());
		movimiento.setOperacion("D");;
		assertThat(movimiento.getOperacion(), is("D"));
	}

	@Test
	public void testCanitdadAnnotation() {
		validate();
		assertThat(constraintViolations.size(), is(4));
		movimiento.setConcepto("c");
		validate();
		assertThat(constraintViolations.size(), is(3));
		movimiento.setFechaValor("fecha");
		movimiento.setOperacion("D");
		movimiento.setImporte(300d);
		validate();
		assertThat(constraintViolations.size(), is(0));
		movimiento.setImporte(-5d);
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("importe"));
	}
	
	private void validate(){
		constraintViolations = validator.validate(movimiento);
	}
}
