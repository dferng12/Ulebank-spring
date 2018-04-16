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

public class SolicitudFinancialAdvisorTest {
	
	private static Validator validator;
	private Set<ConstraintViolation<SolicitudFinancialAdvisor>> constraintViolations;
	private SolicitudFinancialAdvisor solicitud;
	
	@BeforeClass
	public static void setUpClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Before
	public void setUp() throws Exception {
		solicitud = new SolicitudFinancialAdvisor();
	}

	@Test
	public void tesTextoOferta() {
		assertNull(solicitud.getTextoOferta());
		solicitud.setTextoOferta("texto");
		assertThat(solicitud.getTextoOferta(), is("texto"));
		
	}

	@Test
	public void testUrlOferta() {
		assertNull(solicitud.getUrlOferta());
		solicitud.setUrlOferta("url");
		assertThat(solicitud.getUrlOferta(), is("url"));
	}

	@Test
	public void testAsuntoOferta() {
		assertNull(solicitud.getAsuntoOferta());
		solicitud.setAsuntoOferta("asunto");
		assertThat(solicitud.getAsuntoOferta(), is("asunto"));
	}

	@Test
	public void testValidator(){
		solicitud.setAsuntoOferta("asunto");
		solicitud.setTextoOferta("texto");
		solicitud.setUrlOferta("url");
		
		validate();
		assertThat(constraintViolations.size(), is(0));
		
		solicitud.setAsuntoOferta("");
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("asuntoOferta"));
		solicitud.setAsuntoOferta("asunto");
		
		solicitud.setTextoOferta("");
		validate();
		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("textoOferta"));
		solicitud.setTextoOferta("texto");
		
	}
	
	private void validate(){
		constraintViolations = validator.validate(solicitud);
	}
}
