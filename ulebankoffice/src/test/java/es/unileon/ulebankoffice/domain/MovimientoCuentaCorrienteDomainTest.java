package es.unileon.ulebankoffice.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class MovimientoCuentaCorrienteDomainTest {
	
	private MovimientoCuentaCorrienteDomain movimiento;
	private Calendar fecha;

	@Before
	public void setUp() throws Exception {
		fecha = new GregorianCalendar(2017, 04, 23);
		movimiento = new MovimientoCuentaCorrienteDomain(150.00, "Ingreso", fecha.getTime(), "I");
	}
	
	@Test
	public void emptyConstructor(){
		movimiento = new MovimientoCuentaCorrienteDomain();
		assertNull(movimiento.getConcepto());
	}

	@Test
	public void testMovimientoCuentaCorrienteDomain() {
		assertThat(movimiento.getImporte(), is(150d));
	}

	@Test
	public void testGetConcepto() {
		assertThat(movimiento.getConcepto(), is("Ingreso"));
	}
	
	@Test
	public void testSetConcepto(){
		assertThat(movimiento.getConcepto(), is("Ingreso"));
		movimiento.setConcepto("Extracto");
		assertThat(movimiento.getConcepto(), is("Extracto"));
	}

	@Test
	public void testGetFechaValor() {
		assertThat(movimiento.getFechaValor().getTime(), is(fecha.getTime().getTime()));
	}
	
	@Test 
	public void testSetFechaValor(){
		assertThat(movimiento.getFechaValor().getTime(), is(fecha.getTime().getTime()));
		movimiento.setFechaValor(new Date());
		assertThat(movimiento.getFechaValor().getTime(), is(not(fecha.getTime().getTime())));
		
	}
	
	@Test
	public void testGetImporte(){
		assertThat(movimiento.getImporte(), is(150d));
	}
	
	@Test
	public void testSetImporte(){
		assertThat(movimiento.getImporte(), is(150d));
		movimiento.setImporte(150000d);
		assertThat(movimiento.getImporte(), is(150000d));
	}
	
	@Test
	public void testGetOperacion(){
		assertThat(movimiento.getOperacion(), is("I"));
	}
	
	@Test
	public void testSetOperacion(){
		assertThat(movimiento.getOperacion(), is("I"));
		movimiento.setOperacion("D");
		assertThat(movimiento.getOperacion(), is("D"));
	}

}
