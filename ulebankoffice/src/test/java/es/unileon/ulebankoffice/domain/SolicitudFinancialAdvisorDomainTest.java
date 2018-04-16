package es.unileon.ulebankoffice.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class SolicitudFinancialAdvisorDomainTest {
	
	private SolicitudFinancialAdvisorDomain solicitud;
	
	@Before
	public void setUp() throws Exception {
		solicitud = new SolicitudFinancialAdvisorDomain();
	}

	@Test
	public void testTextoOferta() {
		assertNull(solicitud.getTextoOferta());
		solicitud.setTextoOferta("texto");
		assertThat(solicitud.getTextoOferta(), is("texto"));
	}


	@Test
	public void testRutaOferta() {
		assertNull(solicitud.getUrlOferta());
		solicitud.setUrlOferta("ruta");
		assertThat(solicitud.getUrlOferta(), is("ruta"));
	}
	
	@Test
	public void testEstado(){
		assertNull(solicitud.getEstado());
		solicitud.setEstado("abierta");
		assertThat(solicitud.getEstado(), is("abierta"));
	}
	
	@Test
	public void testEmail() {
		assertNull(solicitud.getEmail());
		solicitud.setEmail("email");
		assertThat(solicitud.getEmail(), is("email"));
	}

	@Test
	public void testFileBlobKey() {
		assertNull(solicitud.getFileBlobKey());
		solicitud.setFileBlobKey("blob");;
		assertThat(solicitud.getFileBlobKey(), is("blob"));
	}

	@Test
	public void testGetId() {
		assertNull(solicitud.getId());
	}

	@Test
	public void testAsuntoOferta() {
		assertNull(solicitud.getAsuntoOferta());
		solicitud.setAsuntoOferta("asunto");
		assertThat(solicitud.getAsuntoOferta(), is("asunto"));
	}

	@Test
	public void testRespuestaOferta() {
		assertNull(solicitud.getRespuestaOferta());
		solicitud.setRespuestaOferta("respuesta");;
		assertThat(solicitud.getRespuestaOferta(), is("respuesta"));
	}

	@Test
	public void testFechaCreacion() {
		assertNull(solicitud.getFechaCreacion());
		solicitud.setFechaCreacion("fecha");;
		assertThat(solicitud.getFechaCreacion(), is("fecha"));
	}

}
