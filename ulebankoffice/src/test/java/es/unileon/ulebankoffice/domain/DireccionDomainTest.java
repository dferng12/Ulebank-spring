package es.unileon.ulebankoffice.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DireccionDomainTest {

	private DireccionDomain direccion;

	@Before
	public void setUp() throws Exception {
		direccion = new DireccionDomain("X5526828c", "calleEjemplo", "León", "Castilla y León", 24195, "s/N");
	}

	@Test
	public void testDireccionDomainStringStringStringStringIntString() {
		assertThat(direccion.getCalle(), is("calleEjemplo"));
		assertThat(direccion.getDni().toString(), is("X5526828C"));
	}

	@Test
	public void testDireccionDomainStringStringStringStringHandlerInt() throws DNIException {
		Handler dni = new DNIHandler("x5526828c");
		direccion = new DireccionDomain("calleEjemplo", "León", "Castilla y León", "s/N", dni, 24195);
		assertThat(direccion.getDni().toString(), is("X5526828C"));
		assertThat(direccion.getComunidadAutonoma(), is("Castilla y León"));
		assertThat(direccion.getCodigoPostal(), is(24195));
	}

	@Test
	public void testGetCalle() {
		assertThat(direccion.getCalle(), is("calleEjemplo"));
	}

	@Test
	public void testSetCalle() {
		direccion.setCalle("nuevaCalle");
		assertThat(direccion.getCalle(), is("nuevaCalle"));
	}

	@Test
	public void testGetLocalidad() {
		assertThat(direccion.getLocalidad(), is("León"));
	}

	@Test
	public void testSetLocalidad() {
		direccion.setLocalidad("VillaLeón");
		assertThat(direccion.getLocalidad(), is("VillaLeón"));
	}

	@Test
	public void testGetComunidadAutonoma() {
		assertThat(direccion.getComunidadAutonoma(), is("Castilla y León"));
	}

	@Test
	public void testSetComunidadAutonoma() {
		direccion.setComunidadAutonoma("CastillaLeón");
		assertThat(direccion.getComunidadAutonoma(), is("CastillaLeón"));
	}

	@Test
	public void testGetNumero() {
		assertThat(direccion.getNumero(), is("s/N"));
	}

	@Test
	public void testSetNumero() {
		direccion.setNumero("5");
		assertThat(direccion.getNumero(), is("5"));
	}

	@Test
	public void testGetDni() {
		assertThat(direccion.getDni().toString(), is("X5526828C"));
	}

	@Test
	public void testSetDniHandler() throws DNIException {
		assertThat(direccion.getDni().toString(), is("X5526828C"));
		Handler dni = new DNIHandler("Y9983055B");
		direccion.setDni(dni);
		assertThat(direccion.getDni().toString(), is("Y9983055B"));
	}

	@Test
	public void testSetDniString() throws DNIException {
		assertThat(direccion.getDni().toString(), is("X5526828C"));
		direccion.setDni("Y9983055B");
		assertThat(direccion.getDni().toString(), is("Y9983055B"));
	}

	@Test
	public void testGetCodigoPostal() {
		assertThat(direccion.getCodigoPostal(), is(24195));
	}

	@Test
	public void testSetCodigoPostal() {
		direccion.setCodigoPostal(39400);
		assertThat(direccion.getCodigoPostal(), is(39400));
	}
	
	@Test
	public void testGetId(){
		assertNull(direccion.getId());
	}

	@Test
	public void testToString() {
		assertThat(direccion.toString(), is(
				"DireccionDomain [id=null, calle=calleEjemplo, "
				+ "localidad=León, comunidadAutonoma=Castilla y León, "
				+ "numero=s/N, dni=X5526828C, codigoPostal=24195]"));
	}

}
