package es.unileon.ulebankoffice.domain;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ItalianoTests {

	private ItalianoDomain italiano;
	private double delta = 0.1;
	private double noDelta = 0.0;

	@Before
    public void setUp() throws Exception {
		italiano = new ItalianoDomain(35000, 5.0, 4, 2);
    }
	
	@Test
	public void testSetAndGetTipoInteres() {
		assertEquals(0.05, italiano.getTipoInteres(), noDelta);
		italiano.setTipoInteres(3.25);
		assertEquals(0.0325, italiano.getTipoInteres(), noDelta);
	}
	
	@Test
	public void testSetAndGetCapInicial() {
		assertEquals(35000, italiano.getCapInicial(), noDelta);
		italiano.setCapInicial(35500);
		assertEquals(35500, italiano.getCapInicial(), noDelta);
	}
	
	@Test
	public void testSetAndGetPeriodos() {
		assertEquals(4, italiano.getPeriodos(), noDelta);
		italiano.setPeriodos(3);
		assertEquals(3, italiano.getPeriodos(), noDelta);
	}
	
	@Test
	public void testSetAndGetTipoPeriodo() {
		assertEquals(2, italiano.getTipoPeriodo(), noDelta);
		italiano.setTipoPeriodo(3);
		assertEquals(3, italiano.getTipoPeriodo(), noDelta);
	}
	
	@Test
	public void testCalcular() {
		assertEquals(4484.38, Double.parseDouble(italiano.calcularTabla().get(8).get(1)), delta);
		assertEquals(0, Double.parseDouble(italiano.calcularTabla().get(8).get(4)), delta);
	}
}
