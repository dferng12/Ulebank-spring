package es.unileon.ulebankoffice.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class MovimientosCreditosDomainTest {

	private MovimientosCreditosDomain movimiento;
	private Date fecha;

	@Before
	public void setUp() throws Exception {
		fecha = new SimpleDateFormat("yy-MM-dd").parse("1994-12-05");
		movimiento = new MovimientosCreditosDomain("descripción", 150d, fecha, "D");
	}

	@Test
	public void testDescripcionMovimiento() {
		assertThat(movimiento.getDescripcionMovimiento(), is("descripción"));
		movimiento.setDescripcionMovimiento("otraD");
		assertThat(movimiento.getDescripcionMovimiento(), is("otraD"));
	}

	@Test
	public void testImporteMovimiento() {
		assertThat(movimiento.getImporteMovimiento(), is(150d));
		movimiento.setImporteMovimiento(2500d);
		assertThat(movimiento.getImporteMovimiento(), is(2500d));
	}

	@Test
	public void testFechaMovimiento() throws ParseException {
		assertThat(movimiento.getFechaMovimiento(), is(fecha));
		movimiento.setFechaMovimiento(new SimpleDateFormat("yy-MM-dd").parse("1995-12-05"));
		assertThat(movimiento.getFechaMovimiento(), is(new SimpleDateFormat("yy-MM-dd").parse("1995-12-05")));
	}

	@Test
	public void testProcesado() {
		assertThat(movimiento.isProcesado(), is(false));
		movimiento.setProcesado(true);
		assertThat(movimiento.isProcesado(), is(true));
	}

	@Test
	public void testOperacion() {
		assertThat(movimiento.getOperacion(), is("D"));
		movimiento.setOperacion("I");
		assertThat(movimiento.getOperacion(), is("I"));
	}

}
