/**
 * 
 */
package es.unileon.ulebankoffice.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Razvan Raducu
 *
 */
@Document(collection = "CuentasCorrientes")
public class CuentaCorrienteDomain extends Operacion implements ProductoFinanciero {

	@Id
	private String id;

	private Date fechaApertura;
	private Double interesesAcreedores;
	private Double interesDeudorSobreSaldosNegativos;
	private Double retencionRendimientosCapital;
	private Double comisionMantenimiento;
	private Double comisionDescubierto;
	private Double minimoComisionDescubierto;
	private Date fechaSolicitud;
	private Date fechaResolucion;
	private Date fechaFinalizacion;
	private int diasAnuales;
	private int periodoLiquidacion;

	private String numeroDeCuenta;

	private Double saldo;
	private String estado;

	private Handler dni;

	private List<MovimientoCuentaCorrienteDomain> movimientos;

	private Documentos documentos;
	
	private String interesesAcreedoresFinal;
	private String retencionRendimientosFinal;
	private String interesesDeudoresFinal;
	private String comisionDescubiertoFinal;
	private String totalLiquidacion;

	/**
	 * Constructor utilizado para instanciar manualmente objetos e instanciar
	 * por primera vez una CuentaCorrienteDomain, pasándole el DNI y sin
	 * especificar sus documentos ni sus movimientos, que tendrán que ser
	 * añadidos más adelante.
	 * 
	 * @param fechaApertura
	 * @param interesesAcreedores
	 * @param interesDeudorSobreSaldosNegativos
	 * @param retencionRendimientosCapital
	 * @param comisionMantenimiento
	 * @param comisionDescubierto
	 * @param minimoComisionDescubierto
	 * @param fechaSolicitud
	 * @param saldo
	 * @param estado
	 * @param dni
	 * @param diasAnuales
	 * @param periodoLiquidacion
	 */
	public CuentaCorrienteDomain(Date fechaApertura, Double interesesAcreedores,
			Double interesDeudorSobreSaldosNegativos, Double retencionRendimientosCapital, Double comisionMantenimiento,
			Double comisionDescubierto, Double minimoComisionDescubierto, Date fechaSolicitud, Double saldo,
			String estado, Handler dni, int diasAnuales, int periodoLiquidacion) {
		this.fechaApertura = fechaApertura;
		this.interesesAcreedores = interesesAcreedores;
		this.interesDeudorSobreSaldosNegativos = interesDeudorSobreSaldosNegativos;
		this.retencionRendimientosCapital = retencionRendimientosCapital;
		this.comisionMantenimiento = comisionMantenimiento;
		this.comisionDescubierto = comisionDescubierto;
		this.minimoComisionDescubierto = minimoComisionDescubierto;
		this.fechaSolicitud = fechaSolicitud;
		this.saldo = saldo;
		this.estado = estado;
		this.dni = dni;
		this.diasAnuales = diasAnuales;
		this.periodoLiquidacion = periodoLiquidacion;
	}

	/**
	 * Constructor utilizado para instanciar objetos de esta clase desde MongoDB
	 * 
	 * @param fechaApertura
	 * @param interesesAcreedores
	 * @param interesDeudorSobreSaldosNegativos
	 * @param retencionRendimientosCapital
	 * @param comisionMantenimiento
	 * @param comisionDescubierto
	 * @param minimoComisionDescubierto
	 * @param fechaSolicitud
	 * @param fechaResolucion
	 * @param fechaFinalizacion
	 * @param saldo
	 * @param estado
	 * @param dni
	 * @param movimientos
	 * @param documentos
	 * @param diasAnuales
	 * @param periodoLiquidacion
	 */
	@PersistenceConstructor
	public CuentaCorrienteDomain(Date fechaApertura, Double interesesAcreedores,
			Double interesDeudorSobreSaldosNegativos, Double retencionRendimientosCapital, Double comisionMantenimiento,
			Double comisionDescubierto, Double minimoComisionDescubierto, Date fechaSolicitud, Date fechaResolucion,
			Date fechaFinalizacion, Double saldo, String estado, Handler dni,
			List<MovimientoCuentaCorrienteDomain> movimientos, Documentos documentos, int diasAnuales,
			int periodoLiquidacion) {
		this.fechaApertura = fechaApertura;
		this.interesesAcreedores = interesesAcreedores;
		this.interesDeudorSobreSaldosNegativos = interesDeudorSobreSaldosNegativos;
		this.retencionRendimientosCapital = retencionRendimientosCapital;
		this.comisionMantenimiento = comisionMantenimiento;
		this.comisionDescubierto = comisionDescubierto;
		this.minimoComisionDescubierto = minimoComisionDescubierto;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaResolucion = fechaResolucion;
		this.fechaFinalizacion = fechaFinalizacion;
		this.saldo = saldo;
		this.estado = estado;
		this.dni = dni;
		this.movimientos = movimientos;
		this.documentos = documentos;
		this.diasAnuales = diasAnuales;
		this.periodoLiquidacion = periodoLiquidacion;
	}

	/**
	 * Método para obtener la liquidación entre los periodos indicados. La
	 * liquidación se realizará teniendo en cuenta los movimientos entre las dos
	 * fechas indicadas.
	 * 
	 * @param fechaInicioLiquidacion
	 * @param fechaFinalLiquidacion
	 * @return La tabla que deberá mostrarse.Por petición explícita de Javier,
	 *         el cliente, los números deben expresarse en notación europea.
	 *         12,345.67€. <b>Protip:</b> El resultado de este método es una
	 *         lista de filas donde ya se han formateado los números y se ha
	 *         añadido la moneda, por defecto €. Lo ideal sería poder
	 *         seleccionar la moneda en la página web y que se le pase desde el
	 *         controlador como parámetro a esta clase.
	 */
	public List<List<String>> realizarLiquidacion(Date fechaInicioLiquidacion, Date fechaFinalLiquidacion) {

		List<MovimientoCuentaCorrienteDomain> movimientosLiquidacion = obtenerMovimientosLiquidacion(
				fechaInicioLiquidacion, fechaFinalLiquidacion);

		List<List<String>> tabla = new ArrayList<>();

		List<String> itemTabla;
		List<Double> listaSaldos = new ArrayList<>();
		List<Integer> listaDias = new ArrayList<>();
		List<Double> listaNumerosAcreedores = new ArrayList<>();
		List<Double> listaNumerosDeudores = new ArrayList<>();
		int index = 0;
		DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00");
		decimalFormatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMAN));
		String ingreso = "I";
		String moneda = "€";

		/*
		 * La lógica de esto es imprimir la tabla que se debe mostrar por filas
		 */

		for (MovimientoCuentaCorrienteDomain movimientoCuentaCorrienteDomain : movimientosLiquidacion) {

			itemTabla = new ArrayList<>();

			/* Primera columna: La fecha */
			DateTime fecha = new DateTime(movimientoCuentaCorrienteDomain.getFechaValor());
			itemTabla.add(fecha.getDayOfMonth() + "-" + fecha.getMonthOfYear() + "-" + fecha.getYear());

			/* Segunda columan: El concepto */
			itemTabla.add(movimientoCuentaCorrienteDomain.getConcepto());

			/* Tercera y cuarta columna: Ingresos o disposiciones */
			if (movimientoCuentaCorrienteDomain.getOperacion().equals(ingreso)) {
				itemTabla.add(decimalFormatter.format(movimientoCuentaCorrienteDomain.getImporte()) + moneda);
				itemTabla.add("-");
			} else {
				itemTabla.add("-");
				itemTabla.add(decimalFormatter.format(movimientoCuentaCorrienteDomain.getImporte()) + moneda);
			}

			/* Quinta Columna: El saldo tras computar el movimiento */
			Double saldoConcreto = movimientoCuentaCorrienteDomain.getImporte();

			Double saldoConcretoOperar = movimientoCuentaCorrienteDomain.getOperacion().equals(ingreso) ? saldoConcreto
					: -saldoConcreto;

			if (listaSaldos.isEmpty()) {
				listaSaldos.add(saldoConcretoOperar);
				itemTabla.add(decimalFormatter.format(redondear(listaSaldos.get(index))) + moneda);
			} else {
				listaSaldos.add(saldoConcretoOperar + listaSaldos.get(index - 1));
				itemTabla.add(decimalFormatter.format(redondear(listaSaldos.get(index))) + moneda);
			}

			/* Sexta columna: Los días respecto al siguiente movimiento */
			DateTime nextDay;
			Days days;
			if (index + 1 < movimientosLiquidacion.size()) {
				// Si entra aquí es que no es el último movimiento
				nextDay = new DateTime(movimientosLiquidacion.get(index + 1).getFechaValor());
				days = Days.daysBetween(fecha, nextDay);
				listaDias.add(days.getDays());
			} else {
				nextDay = new DateTime(fechaFinalLiquidacion);
				days = Days.daysBetween(fecha, nextDay);
				listaDias.add(days.getDays());
			}
			itemTabla.add(Integer.toString(days.getDays()));

			/*
			 * Séptima y octava columna: Números Acreedores y Números Deudores
			 */
			Double saldoActual = listaSaldos.get(index);
			if (saldoActual >= 0) {
				listaNumerosAcreedores.add(saldoActual * listaDias.get(index));
				itemTabla.add(decimalFormatter
						.format(redondear(listaNumerosAcreedores.get(listaNumerosAcreedores.size() - 1))) + moneda);
				itemTabla.add("-");
			} else {
				listaNumerosDeudores.add(saldoActual * listaDias.get(index));
				itemTabla.add("-");
				itemTabla.add(
						decimalFormatter.format(redondear(listaNumerosDeudores.get(listaNumerosDeudores.size() - 1)))
								+ moneda);
			}

			tabla.add(itemTabla);
			index++;
		}

		/* Se obtiene la liquidación a partir de los datos computados */
		int totalDias = 0;
		double totalNumerosAcreedores = 0;
		double totalNumerosDeudores = 0;
		double mayorSaldoNegativo = 0;

		for (Integer dias : listaDias) {
			totalDias += dias;
		}

		for (Double nAcreedor : listaNumerosAcreedores) {
			totalNumerosAcreedores += nAcreedor;
		}

		for (Double nDeudores : listaNumerosDeudores) {
			totalNumerosDeudores += nDeudores;
		}

		for (Double saldoAux : listaSaldos) {
			if (saldoAux < 0 && saldoAux < mayorSaldoNegativo) {
				mayorSaldoNegativo = saldoAux;
			}
		}

		double interesesAcreedoresFinalAux = (totalNumerosAcreedores * interesesAcreedores) / (diasAnuales * 100);
		double retencionRendimientosFinalAux = (retencionRendimientosCapital * interesesAcreedoresFinalAux) / 100;
		double interesesDeudoresFinalAux = (totalNumerosDeudores * interesDeudorSobreSaldosNegativos)
				/ (diasAnuales * 100);
		double comisionDescubiertoFinalAux = mayorSaldoNegativo * comisionDescubierto;
		comisionDescubiertoFinalAux = comisionDescubiertoFinalAux < minimoComisionDescubierto ? minimoComisionDescubierto
				: comisionDescubiertoFinalAux;

		interesesAcreedoresFinalAux = redondear(interesesAcreedoresFinalAux);
		retencionRendimientosFinalAux = redondear(retencionRendimientosFinalAux);
		/*
		 * Al ser un número negativo, lo convierto en positivo para más adelante
		 * hacer la resta normal.
		 */
		interesesDeudoresFinalAux = redondear(-interesesDeudoresFinalAux);

		double totalLiquidacionFinal = interesesAcreedoresFinalAux - retencionRendimientosFinalAux - interesesDeudoresFinalAux
				- comisionDescubiertoFinalAux - comisionMantenimiento;
		
				
		/* El último movimiento es la liquidación */
		itemTabla = new ArrayList<>();
		DateTime fechaLiquidacion = new DateTime(fechaFinalLiquidacion);
		itemTabla.add(fechaLiquidacion.getDayOfMonth() + "-" + fechaLiquidacion.getMonthOfYear() + "-" + fechaLiquidacion.getYear());
		itemTabla.add("Liquidacion");
		if (totalLiquidacionFinal >= 0) {
			itemTabla.add(decimalFormatter.format(redondear(totalLiquidacionFinal)) + moneda);
			itemTabla.add("-");
		} else {
			itemTabla.add("-");
			/*
			 * Le cambio el signo para que en la tabla no aparezca como negativo
			 */
			itemTabla.add(decimalFormatter.format(redondear(-totalLiquidacionFinal)) + moneda);
		}
		
		/* Si no se ha añadido ningún movimiento este paso se salta pues listaSaldos.size()-1 causaría un nullPointer */
		if(!listaSaldos.isEmpty()){
		itemTabla
				.add(decimalFormatter.format(redondear(listaSaldos.get(listaSaldos.size() - 1) + totalLiquidacionFinal))
						+ moneda);
		} else{
			itemTabla.add(Double.toString(this.saldo + totalLiquidacionFinal));
					
		}

		itemTabla.add(Integer.toString(totalDias));
		itemTabla.add(decimalFormatter.format(redondear(totalNumerosAcreedores)) + moneda);
		itemTabla.add(decimalFormatter.format(redondear(totalNumerosDeudores)) + moneda);

		tabla.add(itemTabla);
		
		this.interesesAcreedoresFinal = decimalFormatter.format(redondear(interesesAcreedoresFinalAux))+moneda;
		this.retencionRendimientosFinal = decimalFormatter.format(redondear( retencionRendimientosFinalAux))+moneda;
		this.interesesDeudoresFinal = decimalFormatter.format(redondear(interesesDeudoresFinalAux ))+moneda;
		this.comisionDescubiertoFinal =decimalFormatter.format(redondear(comisionDescubiertoFinalAux))+moneda;
		this.totalLiquidacion = decimalFormatter.format(redondear(totalLiquidacionFinal))+moneda;
		
		double liquiTotalAux = totalLiquidacionFinal < 0 ? -totalLiquidacionFinal : totalLiquidacionFinal;
		this.movimientos.add(new MovimientoCuentaCorrienteDomain(redondear(liquiTotalAux), "Liquidación", fechaFinalLiquidacion, "D"));
		this.saldo += totalLiquidacionFinal;
		
		this.saldo = redondear(this.saldo);
		
		return tabla;
	}

	/*
	 * A este método le falta optimización. Lo ideal sería llevar un contador de
	 * movimientos que ya han sido liquidados, de esta forma no se recorrerían
	 * todos siempre. Animo a la persona que esté leyendo esto y que haya venido
	 * detrás de mi a hacerlo ya que yo no he tenido tiempo. En cuanto haya
	 * muchos movimientos si se tiene que recorrer toda la coleccion siempre
	 * esto va a ser luz fuego destrucción.
	 */
	private List<MovimientoCuentaCorrienteDomain> obtenerMovimientosLiquidacion(Date fechaInicio, Date fechaFinal) {

		List<MovimientoCuentaCorrienteDomain> movimientosLiquidacion = new ArrayList<>();
		for (MovimientoCuentaCorrienteDomain movimientoCuentaCorrienteDomain : movimientos) {
			Date fechaMovimiento = movimientoCuentaCorrienteDomain.getFechaValor();
			if (!fechaMovimiento.after(fechaFinal) && !fechaMovimiento.before(fechaInicio)) {
				/*
				 * Puesto que before y after no incluyen los "endpoints", es
				 * decir que after 2017-05-10 es true a partir de 2017-05-11 y
				 * before 2017-05-10 es true a partir de 2017-05-09 y hacia
				 * atrás, es preciso hacer lo siguiente: En lugar de
				 * after(FechaInicio) y before(fechaFinal) que serían exclusive,
				 * no se incluirían las propias fechas, se hace
				 * !after(fechaFinal) y !before(fechaInicio).
				 */
				movimientosLiquidacion.add(movimientoCuentaCorrienteDomain);
			}
		}
		return movimientosLiquidacion;

	}

	private void ordenarMovimientosPorFecha() {
		Collections.sort(this.movimientos, new Comparator<MovimientoCuentaCorrienteDomain>() {
			@Override
			public int compare(MovimientoCuentaCorrienteDomain m1, MovimientoCuentaCorrienteDomain m2) {
				return m1.getFechaValor().compareTo(m2.getFechaValor());
			}
		});
	}

	@Override
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	@Override
	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	@Override
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public Handler getDni() {
		return dni;
	}

	public void setDni(Handler dni) {
		this.dni = dni;
	}

	public List<MovimientoCuentaCorrienteDomain> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientoCuentaCorrienteDomain> movimientos) {
		this.movimientos = movimientos;
	}

	/**
	 * Añade la cantidad indicada al saldo actual de la cuenta
	 * 
	 * @param saldo
	 */
	public void ingresarSaldo(double saldo) {
		this.saldo += saldo;
	}

	/**
	 * Extrae la cantidad indicada del saldo actual de la cuenta
	 * 
	 * @param saldo
	 */
	public void extraerSaldo(double saldo) {
		this.saldo -= saldo;
	}

	/**
	 * Añade a la lista de movimientos el movimiento especificado. En este
	 * método se ordenan automáticamente los movimientos por fecha cada vez que
	 * se añade uno nuevo.
	 * 
	 * @param movimiento
	 */
	public void addMovimiento(MovimientoCuentaCorrienteDomain movimiento) {
		this.movimientos.add(movimiento);
		ordenarMovimientosPorFecha();
	}
	
	/**
	 * Añade el documento a la base de datos así como a la lista de ids de
	 * documentos asociada a esta cuenta corriente.
	 * 
	 * @param documento
	 */
	public void addDocumento(DocumentoAdjuntoDomain documento) {
		documentos.add(documento);
	}

	public List<DocumentoAdjuntoDomain> getDocumentos() throws EmptyCollectionException {
		// Este código sustituye a return documentos.getDocumentos()

		Iterator<DocumentoAdjuntoDomain> iterator = documentos.createIterator();
		List<DocumentoAdjuntoDomain> listaDocs = new ArrayList<>();

		for (iterator.firstElement(); iterator.hasMoreElements(); iterator.nextElement()) {

			listaDocs.add((DocumentoAdjuntoDomain) iterator.currentElement());

		}
		return listaDocs;
	}

	public String getId() {
		return id;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Double getInteresesAcreedores() {
		return interesesAcreedores;
	}

	public void setInteresesAcreedores(Double interesesAcreedores) {
		this.interesesAcreedores = interesesAcreedores;
	}

	public Double getInteresDeudorSobreSaldosNegativos() {
		return interesDeudorSobreSaldosNegativos;
	}

	public void setInteresDeudorSobreSaldosNegativos(Double interesDeudorSobreSaldosNegativos) {
		this.interesDeudorSobreSaldosNegativos = interesDeudorSobreSaldosNegativos;
	}

	public Double getRetencionRendimientosCapital() {
		return retencionRendimientosCapital;
	}

	public void setRetencionRendimientosCapital(Double retencionRendimientosCapital) {
		this.retencionRendimientosCapital = retencionRendimientosCapital;
	}

	public Double getComisionMantenimiento() {
		return comisionMantenimiento;
	}

	public void setComisionMantenimiento(Double comisionMantenimiento) {
		this.comisionMantenimiento = comisionMantenimiento;
	}

	public Double getComisionDescubierto() {
		return comisionDescubierto;
	}

	public void setComisionDescubierto(Double comisionDescubierto) {
		this.comisionDescubierto = comisionDescubierto;
	}

	public Double getMinimoComisionDescubierto() {
		return minimoComisionDescubierto;
	}

	public void setMinimoComisionDescubierto(Double minimoComisionDescubierto) {
		this.minimoComisionDescubierto = minimoComisionDescubierto;
	}

	public int getDiasAnuales() {
		return diasAnuales;
	}

	public void setDiasAnuales(int diasAnuales) {
		this.diasAnuales = diasAnuales;
	}

	public int getPeriodoLiquidacion() {
		return periodoLiquidacion;
	}

	public void setPeriodoLiquidacion(int periodoLiquidacion) {
		this.periodoLiquidacion = periodoLiquidacion;
	}

	public String getNumeroDeCuenta() {
		return numeroDeCuenta;
	}

	public void setNumeroDeCuenta(String numeroDeCuenta) {
		this.numeroDeCuenta = "ES001234123401" + numeroDeCuenta;
	}

	public String getInteresesAcreedoresFinal() {
		return interesesAcreedoresFinal;
	}

	public String getRetencionRendimientosFinal() {
		return retencionRendimientosFinal;
	}

	public String getInteresesDeudoresFinal() {
		return interesesDeudoresFinal;
	}

	public String getComisionDescubiertoFinal() {
		return comisionDescubiertoFinal;
	}

	public String getTotalLiquidacion() {
		return totalLiquidacion;
	}
	
	
}
