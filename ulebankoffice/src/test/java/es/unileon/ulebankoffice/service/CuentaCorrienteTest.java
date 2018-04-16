package es.unileon.ulebankoffice.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Razvan Raducu
 *
 */
public class CuentaCorrienteTest {
	
	private CuentaCorriente cuenta;
	
	@Before
	public void setUp() throws Exception {
		cuenta = new CuentaCorriente();
	}

	@Test
	public void testInteresesAcreedores() {
		assertNull(cuenta.getInteresesAcreedores());
		cuenta.setInteresesAcreedores(2.0);
		assertThat(cuenta.getInteresesAcreedores(), is(2.0));
	}

	@Test
	public void testInteresDeudorSobreSaldosNegativos() {
		assertNull(cuenta.getInteresDeudorSobreSaldosNegativos());
		cuenta.setInteresDeudorSobreSaldosNegativos(2.0);
		assertThat(cuenta.getInteresDeudorSobreSaldosNegativos(), is(2.0));
	}

	@Test
	public void testRetencionRendimientosCapital() {
		assertNull(cuenta.getRetencionRendimientosCapital());
		cuenta.setRetencionRendimientosCapital(2.0);;
		assertThat(cuenta.getRetencionRendimientosCapital(), is(2.0));
	}

	@Test
	public void testComisionMantenimiento() {
		assertNull(cuenta.getComisionMantenimiento());
		cuenta.setComisionMantenimiento(2.0);;
		assertThat(cuenta.getComisionMantenimiento(), is(2.0));
	}

	@Test
	public void testComisionDescubierto() {
		assertNull(cuenta.getComisionDescubierto());
		cuenta.setComisionDescubierto(2.0);;
		assertThat(cuenta.getComisionDescubierto(), is(2.0));
	}

	@Test
	public void testMinimoComisionDescubierto() {
		assertNull(cuenta.getMinimoComisionDescubierto());
		cuenta.setMinimoComisionDescubierto(2.0);;
		assertThat(cuenta.getMinimoComisionDescubierto(), is(2.0));
	}

	@Test
	public void testPeriodoLiquidacion() {
		assertThat(cuenta.getPeriodoLiquidacion(), is(0));
		cuenta.setPeriodoLiquidacion(1);;
		assertThat(cuenta.getPeriodoLiquidacion(), is(1));
	}

	@Test
	public void testDiasAnuales() {
		assertThat(cuenta.getDiasAnuales(), is(0));
		cuenta.setDiasAnuales(360);;
		assertThat(cuenta.getDiasAnuales(), is(360));
	}


}
