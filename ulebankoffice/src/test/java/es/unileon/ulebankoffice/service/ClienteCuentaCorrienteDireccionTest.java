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

public class ClienteCuentaCorrienteDireccionTest {
	
	private static Validator validator;
	private Set<ConstraintViolation<ClienteCuentaCorrienteDireccion>> constraintViolations;
	private ClienteCuentaCorrienteDireccion test;
	
	@BeforeClass
	public static void setUpClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Before
	public void setUp() throws Exception {
		test = new ClienteCuentaCorrienteDireccion();
	}

	@Test
	public void testCliente() {
		assertNull(test.getCliente());
		test.setCliente(new Cliente());
		assertNull(test.getCliente().getDni());
	}


	@Test
	public void testDireccion() {
		assertNull(test.getDireccion());
		test.setDireccion(new Direccion());
		assertNull(test.getDireccion().getCalle());
	}

	@Test
	public void testCuentaCorriente() {
		assertNull(test.getCuentaCorriente());
		test.setCuentaCorriente(new CuentaCorriente());
		assertThat(test.getCuentaCorriente().getPeriodoLiquidacion(), is(0));
	}
	
	@Test
	public void testValidator(){
		test.setCliente(new Cliente());
		test.setCuentaCorriente(new CuentaCorriente());
		test.setDireccion(new Direccion());
		
		validate();
		assertThat(constraintViolations.size(), is(16));
	}

	private void validate(){
		constraintViolations = validator.validate(test);
	}
	
}
