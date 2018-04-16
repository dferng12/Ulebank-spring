package es.unileon.ulebankoffice.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FrancesTests {
	
	private FrancesDomain frances;
	private double delta = 0.1;
	private double noDelta = 0.0;

	@Before
    public void setUp() throws Exception {
        frances = new FrancesDomain(5.0, 35000, 4, 2);
    }

	@Test
	public void testSetAndGetTipoInteres() {
		assertEquals(0.05, frances.getTipoInteres(), noDelta);
		frances.setTipoInteres(3.25);
		assertEquals(0.0325, frances.getTipoInteres(), noDelta);
	}
	
	@Test
	public void testSetAndGetCapInicial() {
		assertEquals(35000, frances.getCapInicial(), noDelta);
		frances.setCapInicial(35500);
		assertEquals(35500, frances.getCapInicial(), noDelta);
	}
	
	@Test
	public void testSetAndGetPeriodos() {
		assertEquals(4, frances.getPeriodos(), noDelta);
		frances.setPeriodos(3);
		assertEquals(3, frances.getPeriodos(), noDelta);
	}
	
	@Test
	public void testSetAndGetTipoPeriodo() {
		assertEquals(2, frances.getTipoPeriodo(), noDelta);
		frances.setTipoPeriodo(3);
		assertEquals(3, frances.getTipoPeriodo(), noDelta);
	}
	
	@Test
	public void testCalcular() {
		assertEquals(4881.36, Double.parseDouble(frances.calcularTabla().get(8).get(1)), delta);
		assertEquals(0, Double.parseDouble(frances.calcularTabla().get(8).get(4)), delta);
	}
}
