package es.unileon.ulebankoffice.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DescuentosTests {
	
	private DescuentosDomain descuentos;
	private double delta = 0.1;
	private double noDelta = 0.0;
	
	@Before
	public void setUp() throws Exception {
		descuentos = new DescuentosDomain(300000, 14, 5.25, 360, 0.75, 0.4);
	}
	
	@Test
	public void testSetAndGetImporteDescuento() {
		assertEquals(300000, descuentos.getImporteDescuento(), noDelta);
		descuentos.setImporteDescuento(13500);
		assertEquals(13500, descuentos.getImporteDescuento(), noDelta);
	}
	
	@Test
	public void testSetAndGetPeriodoDescuento() {
		assertEquals(14, descuentos.getPeriodoDescuento(), noDelta);
		descuentos.setPeriodoDescuento(50);
		assertEquals(50, descuentos.getPeriodoDescuento(), noDelta);
	}
	
	@Test
	public void testSetAndGetTipoInteres() {
		assertEquals(0.0525, descuentos.getTipoInteres(), noDelta);
		descuentos.setTipoInteres(6.75);
		assertEquals(0.0675, descuentos.getTipoInteres(), noDelta);
	}
	
	@Test
	public void testSetAndGetOtrosGastos() {
		assertEquals(0.75, descuentos.getOtrosGastos(), noDelta);
		descuentos.setOtrosGastos(600);
		assertEquals(600, descuentos.getOtrosGastos(), noDelta);
	}
	
	@Test
	public void testSetAndGetComisiones() {
		assertEquals(0.004, descuentos.getComisiones(), noDelta);
		descuentos.setComisiones(15);
		assertEquals(0.015, descuentos.getComisiones(), noDelta);
	}
	
	@Test
	public void testCalcular() {
		assertEquals(298186.75, Double.parseDouble(descuentos.calcularTabla().get(0).get(3)), delta);
	}
}
