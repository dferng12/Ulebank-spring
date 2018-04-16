package es.unileon.ulebankoffice.domain;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;

import es.unileon.ulebankoffice.configuration.MongoTestConfig;
import es.unileon.ulebankoffice.repository.DocumentoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfig.class)
public class CuentaCorrienteDomainTest {
	
	@Autowired
	Mongo mongo;

	@Autowired
	private ApplicationContext applicationContext;

	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("ulebankofficetestdb");
	
	@Autowired
	private DocumentoRepository repo;
	
	private CuentaCorrienteDomain cuenta;
	private SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");

	@Before
	public void setUp() throws Exception {
		cuenta = new CuentaCorrienteDomain(df.parse("2017-03-01"), 1d, 23d, 20d, 25d, 3d, 50d, df.parse("2017-03-01"),
				150d, "activa", new DNIHandler("x5526828C"), 360, 4);
		cuenta.setMovimientos(new ArrayList<MovimientoCuentaCorrienteDomain>());
		cuenta.addMovimiento(new MovimientoCuentaCorrienteDomain(100d, "Apertura cuenta", df.parse("2017-03-01"), "I"));
		cuenta.addMovimiento(new MovimientoCuentaCorrienteDomain(1200d, "Nómina", df.parse("2017-03-30"), "I"));
		cuenta.addMovimiento(
				new MovimientoCuentaCorrienteDomain(720d, "Tarjeta de crédito", df.parse("2017-03-31"), "D"));
		cuenta.addMovimiento(new MovimientoCuentaCorrienteDomain(600d, "Recibo hipoteca", df.parse("2017-04-02"), "D"));
		cuenta.addMovimiento(new MovimientoCuentaCorrienteDomain(1200d, "Nómina", df.parse("2017-04-30"), "I"));
		cuenta.addMovimiento(new MovimientoCuentaCorrienteDomain(300d, "Ingreso IRPF", df.parse("2017-06-18"), "I"));
	}
	
	@After
	public void afterEachTest(){
		repo.deleteAll();
	}

	@Test
	public void testCuentaCorrienteDomainDateDoubleDoubleDoubleDoubleDoubleDoubleDateDateDateDoubleStringHandlerListOfMovimientoCuentaCorrienteDomainDocumentosIntInt() throws ParseException, DNIException {
		List <MovimientoCuentaCorrienteDomain> listaMovimientos = new ArrayList<>();
		listaMovimientos.add(new MovimientoCuentaCorrienteDomain(100d, "Apertura cuenta", df.parse("2017-03-01"), "I"));
		listaMovimientos.add(new MovimientoCuentaCorrienteDomain(1200d, "Nómina", df.parse("2017-03-30"), "I"));
		listaMovimientos.add(new MovimientoCuentaCorrienteDomain(720d, "Tarjeta de crédito", df.parse("2017-03-31"), "D"));
		listaMovimientos.add(new MovimientoCuentaCorrienteDomain(600d, "Recibo hipoteca", df.parse("2017-04-02"), "D"));
		listaMovimientos.add(new MovimientoCuentaCorrienteDomain(1200d, "Nómina", df.parse("2017-04-30"), "I"));
		listaMovimientos.add(new MovimientoCuentaCorrienteDomain(300d, "Ingreso IRPF", df.parse("2017-06-18"), "I"));
		
		cuenta = new CuentaCorrienteDomain(df.parse("2017-03-01"), 1d, 23d, 20d, 25d, 3d, 50d, df.parse("2017-03-01"), null, null, 150d, "activa", new DNIHandler("x5526828C"), listaMovimientos, new Documentos(new ArrayList<String>()), 360, 4);
		
		assertThat(cuenta.getEstado(), is("activa"));
		assertThat(cuenta.getDni().toString(), is("X5526828C"));
		
	}

	@Test
	public void testRealizarLiquidacion() throws ParseException {
		assertThat(cuenta.getMovimientos().size(), is(6));
		List<List<String>> resultado = cuenta.realizarLiquidacion(df.parse("2017-03-01"), df.parse("2017-06-30"));
		assertThat(resultado.get(resultado.size() - 1).get(3), is("73,56€"));
		assertThat(resultado.get(resultado.size() - 1).get(4), is("1.406,44€"));
		assertThat(resultado.get(resultado.size() - 1).get(5), is("121"));
	}

	@Test
	public void testGetFechaSolicitud() throws ParseException {
		assertThat(cuenta.getFechaSolicitud(), is(df.parse("2017-03-01")));
	}

	@Test
	public void testSetFechaSolicitud() throws ParseException {
		assertThat(cuenta.getFechaSolicitud(), is(df.parse("2017-03-01")));
		cuenta.setFechaSolicitud(df.parse("1994-12-05"));
		assertThat(cuenta.getFechaSolicitud(), is(df.parse("1994-12-05")));
	}

	@Test
	public void testGetFechaResolucion() {
		assertNull(cuenta.getFechaResolucion());
	}

	@Test
	public void testSetFechaResolucion() throws ParseException {
		assertNull(cuenta.getFechaResolucion());
		cuenta.setFechaResolucion(df.parse("1994-12-05"));
		assertThat(cuenta.getFechaResolucion(), is(df.parse("1994-12-05")));
	}

	@Test
	public void testGetFechaFinalizacion() {
		assertNull(cuenta.getFechaFinalizacion());
	}

	@Test
	public void testSetFechaFinalizacion() throws ParseException {
		assertNull(cuenta.getFechaFinalizacion());
		cuenta.setFechaFinalizacion(df.parse("1994-12-05"));
		assertThat(cuenta.getFechaFinalizacion(), is(df.parse("1994-12-05")));
	}

	@Test
	public void testGetSaldo() {
assertThat(cuenta.getSaldo(), is(150d));
	}

	@Test
	public void testSetSaldo() {
		assertThat(cuenta.getSaldo(), is(150d));
		cuenta.setSaldo(150150d);
		assertThat(cuenta.getSaldo(), is(150150d));
	}

	@Test
	public void testGetEstado() {
		assertThat(cuenta.getEstado(), is("activa"));
	}

	@Test
	public void testSetEstado() {
		assertThat(cuenta.getEstado(), is("activa"));
		cuenta.setEstado("congeladaPorElFBI");
		assertThat(cuenta.getEstado(), is("congeladaPorElFBI"));
	}

	@Test
	public void testGetDni() {
		assertThat(cuenta.getDni(), isA(Handler.class));
		assertThat(cuenta.getDni().toString(), is("X5526828C"));
	}

	@Test
	public void testSetDni() throws DNIException {
		assertThat(cuenta.getDni(), isA(Handler.class));
		assertThat(cuenta.getDni().toString(), is("X5526828C"));
		cuenta.setDni(new DNIHandler("41914614N"));
		assertThat(cuenta.getDni().toString(), is("41914614N"));
	}

	@Test
	public void testGetMovimientos() {
		assertThat(cuenta.getMovimientos().size(), is(6));

	}

	@Test
	public void testSetMovimientos() {
		assertThat(cuenta.getMovimientos().size(), is(6));
		cuenta.setMovimientos(null);
		assertNull(cuenta.getMovimientos());
	}

	@Test
	public void testIngresarSaldo() {
		assertThat(cuenta.getSaldo(), is(150d));
		cuenta.ingresarSaldo(150d);
		assertThat(cuenta.getSaldo(), is(300d));
	}

	@Test
	public void testExtraerSaldo() {
		assertThat(cuenta.getSaldo(), is(150d));
		cuenta.extraerSaldo(150d);
		assertThat(cuenta.getSaldo(), is(0.0));
	}

	@Test
	public void testAddMovimiento() throws ParseException {
		assertThat(cuenta.getMovimientos().size(), is(6));
		cuenta.addMovimiento(new MovimientoCuentaCorrienteDomain(1200d, "Nómina", df.parse("2017-03-30"), "I"));
		assertThat(cuenta.getMovimientos().size(), is(7));
	}

	@Test
	public void testAddDocumento() throws EmptyCollectionException {
		Documentos documentos = new Documentos(new ArrayList<String>());
		ReflectionTestUtils.setField(documentos, "repo", repo);
		
		ReflectionTestUtils.setField(cuenta, "documentos", documentos);
		
		assertThat(cuenta.getDocumentos().size(), is(0));
		cuenta.addDocumento(new DocumentoAdjuntoDomain("ruta", "name"));
		assertThat(cuenta.getDocumentos().size(), is(1));
		
	}

	@Test
	public void testGetDocumentos() throws EmptyCollectionException {
		Documentos documentos = new Documentos(new ArrayList<String>());
		ReflectionTestUtils.setField(documentos, "repo", repo);
		
		ReflectionTestUtils.setField(cuenta, "documentos", documentos);
		
		assertThat(cuenta.getDocumentos().size(), is(0));
		
	}

	@Test
	public void testGetFechaApertura() throws ParseException {
		assertThat(cuenta.getFechaApertura(), is(df.parse("2017-03-01")));
	}

	@Test
	public void testSetFechaApertura() throws ParseException {
		assertThat(cuenta.getFechaApertura(), is(df.parse("2017-03-01")));
		cuenta.setFechaApertura(df.parse("2017-03-02"));
		assertThat(cuenta.getFechaApertura(), is(df.parse("2017-03-02")));
	}

	@Test
	public void testGetInteresesAcreedores() {
		assertThat(cuenta.getInteresesAcreedores(), is(1d));
	}

	@Test
	public void testSetInteresesAcreedores() {
		assertThat(cuenta.getInteresesAcreedores(), is(1d));
		cuenta.setInteresesAcreedores(2d);
		assertThat(cuenta.getInteresesAcreedores(), is(2d));
	}

	@Test
	public void testGetInteresDeudorSobreSaldosNegativos() {
		assertThat(cuenta.getInteresDeudorSobreSaldosNegativos(), is(23d));
	}

	@Test
	public void testSetInteresDeudorSobreSaldosNegativos() {
		assertThat(cuenta.getInteresDeudorSobreSaldosNegativos(), is(23d));
		cuenta.setInteresDeudorSobreSaldosNegativos(24d);
		assertThat(cuenta.getInteresDeudorSobreSaldosNegativos(), is(24d));
	}

	@Test
	public void testGetRetencionRendimientosCapital() {
		assertThat(cuenta.getRetencionRendimientosCapital(), is(20d));
	}

	@Test
	public void testSetRetencionRendimientosCapital() {
		assertThat(cuenta.getRetencionRendimientosCapital(), is(20d));
		cuenta.setRetencionRendimientosCapital(1850d);
		assertThat(cuenta.getRetencionRendimientosCapital(), is(1850d));
	}

	@Test
	public void testGetComisionMantenimiento() {
		assertThat(cuenta.getComisionMantenimiento(), is(25d));
	}

	@Test
	public void testSetComisionMantenimiento() {
		assertThat(cuenta.getComisionMantenimiento(), is(25d));
		cuenta.setComisionMantenimiento(250d);
		assertThat(cuenta.getComisionMantenimiento(), is(250d));
	}

	@Test
	public void testGetComisionDescubierto() {
		assertThat(cuenta.getComisionDescubierto(), is(3d));
	}

	@Test
	public void testSetComisionDescubierto() {
		assertThat(cuenta.getComisionDescubierto(), is(3d));
		cuenta.setComisionDescubierto(201d);
		assertThat(cuenta.getComisionDescubierto(), is(201d));
	}

	@Test
	public void testGetMinimoComisionDescubierto() {
		assertThat(cuenta.getMinimoComisionDescubierto(), is(50d));
	}

	@Test
	public void testSetMinimoComisionDescubierto() {
		assertThat(cuenta.getMinimoComisionDescubierto(), is(50d));
		cuenta.setMinimoComisionDescubierto(55d);
		assertThat(cuenta.getMinimoComisionDescubierto(), is(55d));
	}

	@Test
	public void testGetDiasAnuales() {
		assertThat(cuenta.getDiasAnuales(), is(360));
	}

	@Test
	public void testSetDiasAnuales() {
		assertThat(cuenta.getDiasAnuales(), is(360));
		cuenta.setDiasAnuales(365);
		assertThat(cuenta.getDiasAnuales(), is(365));
	}

	@Test
	public void testGetPeriodoLiquidacion() {
		assertThat(cuenta.getPeriodoLiquidacion(), is(4));
	}

	@Test
	public void testSetPeriodoLiquidacion() {
		assertThat(cuenta.getPeriodoLiquidacion(), is(4));
		cuenta.setPeriodoLiquidacion(5);
		assertThat(cuenta.getPeriodoLiquidacion(), is(5));
	}
	
}
