package es.unileon.ulebankoffice.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DireccionTest {
	
	private Direccion direccion;
	
	@Before
	public void setUp() throws Exception {
		direccion = new Direccion();
	}

	@Test
	public void testCalle() {
		assertNull(direccion.getCalle());
		direccion.setCalle("calle");
		assertThat(direccion.getCalle(), is("calle"));
	}


	@Test
	public void testNumero() {
		assertNull(direccion.getNumero());
		direccion.setNumero("sinNumero");
		assertThat(direccion.getNumero(), is("sinNumero"));
	}

	@Test
	public void testCodigoPostal() {
		assertNotNull(direccion.getCodigoPostal());
		assertThat(direccion.getCodigoPostal(), is(0));
		direccion.setCodigoPostal(20007);
		assertThat(direccion.getCodigoPostal(), is(20007));
	}


	@Test
	public void testLocalidad() {
		assertNull(direccion.getLocalidad());
		direccion.setLocalidad("localidad");
		assertThat(direccion.getLocalidad(), is("localidad"));
	}


	@Test
	public void testComunidadAutonoma() {
		assertNull(direccion.getComunidadAutonoma());
		direccion.setComunidadAutonoma("comunidad");
		assertThat(direccion.getComunidadAutonoma(), is("comunidad"));
	}

	@Test
	public void testToString() {
		assertThat(direccion.toString(), is("Direccion [calle=null, numero=null, codigoPostal=0, localidad=null, comunidadAutonoma=null]"));
	}

}
