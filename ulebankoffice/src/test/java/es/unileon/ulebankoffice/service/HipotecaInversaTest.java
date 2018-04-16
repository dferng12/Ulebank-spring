package es.unileon.ulebankoffice.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HipotecaInversaTest {

	private static Validator validator;
	private Set<ConstraintViolation<HipotecaInversa>> constraintViolations;
	private HipotecaInversa hipoteca;
	
	@BeforeClass
	public static void setUpClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void setUp() throws Exception {
		hipoteca = new HipotecaInversa();
	}

	@Test
	public void testValorTasacion() {
		assertThat(hipoteca.getValorTasacion(), is(0.0));
		hipoteca.setValorTasacion(1.1);
		assertThat(hipoteca.getValorTasacion(), is(1.1));
	}



	@Test
	public void testEdad() {
		assertThat(hipoteca.getEdad(), is(0));
		hipoteca.setEdad(5);
		assertThat(hipoteca.getEdad(), is(5));
	}


	@Test
	public void testPorcentajeSobreTasacion() {
		assertThat(hipoteca.getPorcentajeSobreTasacion(), is(0.0));
		hipoteca.setPorcentajeSobreTasacion(5.0);
		assertThat(hipoteca.getPorcentajeSobreTasacion(), is(5.0));
	}

	@Test
	public void testTipoInteresPrestamo() {
		assertThat(hipoteca.getTipoInteresPrestamo(), is(0.0));
		hipoteca.setTipoInteresPrestamo(5.0);
		assertThat(hipoteca.getTipoInteresPrestamo(), is(5.0));
	}

	@Test
	public void testComisionApertura() {
		assertThat(hipoteca.getComisionApertura(), is(0.0));
		hipoteca.setComisionApertura(5.0);
		assertThat(hipoteca.getComisionApertura(), is(5.0));
	}

	@Test
	public void testRentabilidadRenta() {
		assertThat(hipoteca.getRentabilidadRenta(), is(0.0));
		hipoteca.setRentabilidadRenta(5.0);
		assertThat(hipoteca.getRentabilidadRenta(), is(5.0));
	}

	@Test
	public void testCosteTasacion() {
		assertThat(hipoteca.getCosteTasacion(), is(0.0));
		hipoteca.setCosteTasacion(5.0);
		assertThat(hipoteca.getCosteTasacion(), is(5.0));
	}


	@Test
	public void testGastosFormalizacion() {
		assertThat(hipoteca.getGastosFormalizacion(), is(0.0));
		hipoteca.setGastosFormalizacion(5.0);
		assertThat(hipoteca.getGastosFormalizacion(), is(5.0));
	}
	
	@Test
	public void testValidator(){
		
		hipoteca.setValorTasacion(-1.0);
		hipoteca.setEdad(67);
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("valorTasacion"));
		hipoteca.setValorTasacion(1.0);
		validate();
		assertThat(constraintViolations.size(), is(0));
		
		hipoteca.setEdad(63);
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("edad"));
		
		hipoteca.setEdad(69);
		validate();
		assertThat(constraintViolations.size(), is(0));
		
		hipoteca.setEdad(90);
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("edad"));
		hipoteca.setEdad(67);
		
		/* Las etiquetas son iguales para todos. Por eso se ha optado por no repetir el mismo test
		 * para todos los atributos de la clase que tienen las mismas etiquetas */
		hipoteca.setPorcentajeSobreTasacion(-5.0);
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("porcentajeSobreTasacion"));
		
		
	}
	
	private void validate(){
		constraintViolations = validator.validate(hipoteca);
	}



}
