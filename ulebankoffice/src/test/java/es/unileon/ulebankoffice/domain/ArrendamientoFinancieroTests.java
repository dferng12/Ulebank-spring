package es.unileon.ulebankoffice.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ArrendamientoFinancieroTests {
	
	private ArrendamientoFinancieroDomain arrendamientoFinanciero;
	private double delta = 0.1;
	private double noDelta = 0.0;
	
	@Before
	public void setUp() throws Exception {
		arrendamientoFinanciero = new ArrendamientoFinancieroDomain(50000, 5, 2, 10);
	}

	@Test
	public void testSetAndGetValorBien() {
		assertEquals(50000, arrendamientoFinanciero.getValorBien(), noDelta);
		arrendamientoFinanciero.setValorBien(15000);
		assertEquals(15000, arrendamientoFinanciero.getValorBien(), noDelta);
	}
	
	@Test
	public void testSetAndGetDuracionContrato() {
		assertEquals(5, arrendamientoFinanciero.getDuracionContrato(), noDelta);
		arrendamientoFinanciero.setDuracionContrato(4);
		assertEquals(4, arrendamientoFinanciero.getDuracionContrato(), noDelta);
	}
	
	@Test
	public void testSetAndGetFraccionamientoPagoCuota() {
		assertEquals(2, arrendamientoFinanciero.getFraccionamientoPagoCuota(), noDelta);
		arrendamientoFinanciero.setFraccionamientoPagoCuota(3);
		assertEquals(3, arrendamientoFinanciero.getFraccionamientoPagoCuota(), noDelta);
	}
	
	@Test
	public void testSetAndGetTipoInteres() {
		assertEquals(0.10, arrendamientoFinanciero.getTipoInteres(), noDelta);
		arrendamientoFinanciero.setTipoInteres(15);
		assertEquals(0.15, arrendamientoFinanciero.getTipoInteres(), noDelta);
	}
	
	@Test
	public void testCalcular() {
		assertEquals(5732.80, Double.parseDouble(arrendamientoFinanciero.calcularTabla().get(10).get(1)), delta);
		assertEquals(0.0, Double.parseDouble(arrendamientoFinanciero.calcularTabla().get(10).get(4)), delta);
	}
}
