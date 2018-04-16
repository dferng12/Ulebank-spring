package es.unileon.ulebankoffice.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AmericanoTests {
	
	private AmericanoDomain americano;
	private double delta = 0.1;
	private double noDelta = 0.0;
	
	@Before
	public void setUp() throws Exception {
		americano = new AmericanoDomain(50000, 10, 5, 2);
	}
	
	@Test
	public void testSetAndGetTipoInteres() {
		assertEquals(0.10, americano.getTipoInteres(), noDelta);
		americano.setTipoInteres(3.25);
		assertEquals(0.0325, americano.getTipoInteres(), noDelta);
	}
	
	@Test
	public void testSetAndGetCapInicial() {
		assertEquals(50000, americano.getCapInicial(), noDelta);
		americano.setCapInicial(5000);
		assertEquals(5000, americano.getCapInicial(), noDelta);
	}
	
	@Test
	public void testSetAndGetPeriodos() {
		assertEquals(5, americano.getPeriodos(), noDelta);
		americano.setPeriodos(3);
		assertEquals(3, americano.getPeriodos(), noDelta);
	}
	
	@Test
	public void testSetAndGetTipoPeriodo() {
		assertEquals(2, americano.getTipoPeriodo(), noDelta);
		americano.setTipoPeriodo(6);
		assertEquals(6, americano.getTipoPeriodo(), noDelta);
	}
	
	@Test
	public void testCalcular() {
		assertEquals(52500, Double.parseDouble(americano.calcularTabla().get(10).get(1)), delta);
	}
}
