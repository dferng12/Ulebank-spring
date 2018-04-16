package es.unileon.ulebankoffice.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HipotecaInversaTests {
	
	private HipotecaInversaDomain hipotecaInversa;
	private double delta = 0.1;
	private double noDelta = 0.0;
	
	@Before
	public void setUp() throws Exception {
		hipotecaInversa = new HipotecaInversaDomain(260000, 75, 70, 4.70, 0.50, 3.15, 300, 3871);
	}

	@Test
	public void testSetAndGetValorTasacion() {
		assertEquals(260000, hipotecaInversa.getValorTasacion(), noDelta);
		hipotecaInversa.setValorTasacion(158000);
		assertEquals(158000, hipotecaInversa.getValorTasacion(), noDelta);
	}
	
	@Test
	public void testSetAndGetEdad() {
		assertEquals(75, hipotecaInversa.getEdad());
		hipotecaInversa.setEdad(72);
		assertEquals(72, hipotecaInversa.getEdad());
	}
	
	@Test
	public void testSetAndGetPorcentajeSobreTasacion() {
		assertEquals(0.7, hipotecaInversa.getPorcentajeSobreTasacion(), noDelta);
		hipotecaInversa.setPorcentajeSobreTasacion(64);
		assertEquals(0.64, hipotecaInversa.getPorcentajeSobreTasacion(), noDelta);
	}
	
	@Test
	public void testSetAndGetTipoInteresPrestamo() {
		assertEquals(0.047, hipotecaInversa.getTipoInteresPrestamo(), delta);
		hipotecaInversa.setTipoInteresPrestamo(3.90);
		assertEquals(0.039, hipotecaInversa.getTipoInteresPrestamo(), delta);
	}
	
	@Test
	public void testSetAndGetComisionApertura() {
		assertEquals(0.005, hipotecaInversa.getComisionApertura(), delta);
		hipotecaInversa.setComisionApertura(0.62);
		assertEquals(0.0062, hipotecaInversa.getComisionApertura(), delta);
	}
	
	@Test
	public void testSetAndGetRentabilidadRenta() {
		assertEquals(0.0315, hipotecaInversa.getRentabilidadRenta(), delta);
		hipotecaInversa.setRentabilidadRenta(2.95);
		assertEquals(0.0295, hipotecaInversa.getRentabilidadRenta(), delta);
	}
	
	@Test
	public void testSetAndGetCosteTasacion() {
		assertEquals(300, hipotecaInversa.getCosteTasacion(), noDelta);
		hipotecaInversa.setCosteTasacion(295);
		assertEquals(295, hipotecaInversa.getCosteTasacion(), noDelta);
	}
	
	@Test
	public void testSetAndGetGastosFormalizacion() {
		assertEquals(3871, hipotecaInversa.getGastosFormalizacion(), noDelta);
		hipotecaInversa.setGastosFormalizacion(3658);
		assertEquals(3658, hipotecaInversa.getGastosFormalizacion(), noDelta);
	}
	
	@Test
	public void testCalcular() {
		assertEquals(535.60, Double.parseDouble(hipotecaInversa.calcularTabla().get(0).get(4)), delta);
	}
}