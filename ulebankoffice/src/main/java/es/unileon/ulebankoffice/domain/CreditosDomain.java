package es.unileon.ulebankoffice.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Clase encargada de proporcionar la lógica de negocio asociada al servicio
 * Cuentas de Crédito.
 * 
 * @author Razvan Raducu, Alexis Gutierrez
 *
 */
public class CreditosDomain extends Operacion {

	private double limiteCredito;
	private Date fechaApertura;
	private Date fechaVencimiento;
	private double interesDeudor;
	private double interesExcedido;
	private double interesAcreedor;
	private double comisionSMND;
	private List<MovimientosCreditosDomain> movimientos;
	private List<List<String>> tabla;
	private List<Integer> dias;
	private List<Double> numerosDeudores;
	private List<Double> numerosExcedidos;
	private List<Double> numerosAcreedores;

	/*
	 * Se hace uso de un decimalFormatter para qvitar la notación científica ya
	 * que la gente de finanzas expresamente lo quiere así.
	 */
	private static final DecimalFormat DECIMALFORMATTER = new DecimalFormat("#,##0.00");
	private static final String MONEDA = "€";
	private static final String INGRESO = "I";

	/**
	 * @param limiteCredito
	 * @param fechaApertura
	 * @param fechavencimiento
	 * @param interesDeudor
	 * @param interesExcedido
	 * @param interesAcreedor
	 * @param comisionSMND
	 * @param movimientos
	 */
	public CreditosDomain(double limiteCredito, Date fechaApertura, Date fechavencimiento, double interesDeudor,
			double interesExcedido, double interesAcreedor, double comisionSMND,
			List<MovimientosCreditosDomain> movimientos) {

		this.limiteCredito = limiteCredito;
		this.fechaApertura = fechaApertura;
		this.fechaVencimiento = fechavencimiento;
		this.interesDeudor = interesDeudor / 100.0;
		this.interesExcedido = interesExcedido / 100.0;
		this.interesAcreedor = interesAcreedor / 100.0;
		this.comisionSMND = comisionSMND / 100.0;
		this.movimientos = movimientos;

		DECIMALFORMATTER.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMAN));
	}

	/**
	 * Método para incluir la comisión de apertura y el corretaje como
	 * movimientos de la cuenta de crédito.
	 * 
	 * @param comisionApertura
	 * @param corretaje
	 */
	public void incluirComsionAperturaYCorretaje(double comisionApertura, double corretaje) {
		double importeComisionApertura = limiteCredito * (comisionApertura / 100);
		double importeCorretaje = limiteCredito * (corretaje / 1000.0);

		movimientos.add(0, new MovimientosCreditosDomain("Corretaje", importeCorretaje, fechaApertura, "D"));
		movimientos.add(0,
				new MovimientosCreditosDomain("Comision de apertura", importeComisionApertura, fechaApertura, "D"));
	}

	public List<List<String>> calcularTabla() {

		ordenarMovimientosPorFecha();
		cambiarSignoImportes();

		List<Double> saldo = new ArrayList<>();
		dias = new ArrayList<>();
		numerosDeudores = new ArrayList<>();
		numerosExcedidos = new ArrayList<>();
		numerosAcreedores = new ArrayList<>();

		tabla = new ArrayList<>();
		List<String> itemTabla;
		Calendar calendar = new GregorianCalendar();

		int index = 0;

		for (MovimientosCreditosDomain movimiento : movimientos) {

			// Puesto que un bucle foreach no se puede terminar con un break
			// (las fechas están en orden) simplemente compruebo que el
			// movmiento actual ocurre antes que la fecha de vencimiento.
			if (movimiento.getFechaMovimiento().before(fechaVencimiento)) {

				itemTabla = new ArrayList<>();

				/* Fecha valor */
				calendar.setTime(movimiento.getFechaMovimiento());
					/* Atencion. Se suma 1 puesto que en el calendario Gregoriano las fechas empiezan en 0. Enero el es el mes 0 y Diciembre el 11. */
				itemTabla.add(calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1));
				
				/* Concepto */
				itemTabla.add(movimiento.getDescripcionMovimiento());

				/* Disposiciones o ingresos */
				if (movimiento.getOperacion().equals(INGRESO)) {
					itemTabla.add("-");
					/*
					 * Atención. Le vuelvo a cambiar de signo para que no se
					 * muestra en la tabla como un número negativo.
					 */
					itemTabla.add(String.valueOf(DECIMALFORMATTER.format(-movimiento.getImporteMovimiento())) + MONEDA);
				} else {
					itemTabla.add(String.valueOf(DECIMALFORMATTER.format(movimiento.getImporteMovimiento())) + MONEDA);
					itemTabla.add("-");
				}
				
				/* Saldo */
				if (saldo.isEmpty()) {
					saldo.add(movimiento.getImporteMovimiento());
					itemTabla.add(DECIMALFORMATTER.format(redondear(movimiento.getImporteMovimiento())) + MONEDA);

				} else {
					saldo.add(movimiento.getImporteMovimiento() + saldo.get(index - 1));
					itemTabla.add(DECIMALFORMATTER
							.format(redondear(movimiento.getImporteMovimiento() + saldo.get(index - 1))) + MONEDA);

				}

				/* Días */
				DateTime thisDay;
				DateTime nextDay;
				Days days;
				thisDay = new DateTime(movimiento.getFechaMovimiento());
				if (index + 1 < movimientos.size()) {
					nextDay = new DateTime(movimientos.get(index + 1).getFechaMovimiento());
					days = Days.daysBetween(thisDay, nextDay);
					dias.add(days.getDays());
				} else {
					nextDay = new DateTime(fechaVencimiento);
					days = Days.daysBetween(thisDay, nextDay);
					dias.add(days.getDays());
				}
				itemTabla.add(Integer.toString(days.getDays()));

				/* N.Deudores / N.Excedidos / N.Acreedores */
				if (saldo.get(index) > limiteCredito) {
					double numerosDeudoresAux = limiteCredito * dias.get(index);
					numerosDeudores.add(numerosDeudoresAux);
					itemTabla.add(DECIMALFORMATTER.format(numerosDeudoresAux) + MONEDA);
					double diferencia = saldo.get(index) - limiteCredito;
					numerosExcedidos.add(diferencia * dias.get(index));

					itemTabla.add(DECIMALFORMATTER.format(diferencia * dias.get(index)) + MONEDA);
					itemTabla.add("-");
				} else if (saldo.get(index) < 0) {
					numerosAcreedores.add(dias.get(index) * saldo.get(index) * (-1));
					itemTabla.add("-");
					itemTabla.add("-");
					itemTabla.add(DECIMALFORMATTER.format(dias.get(index) * saldo.get(index) * (-1)) + MONEDA);

				} else {
					numerosDeudores.add(saldo.get(index) * dias.get(index));
					itemTabla.add(DECIMALFORMATTER.format(saldo.get(index) * dias.get(index)) + MONEDA);
					itemTabla.add("-");
					itemTabla.add("-");

				}

				tabla.add(itemTabla);

				index++;
			}
		}

		return tabla;
	}

	/**
	 * Método que calcula la liquidación de la cuenta en función de los
	 * parámetros ya especificados. Para que este método funcione, primero se
	 * tiene que instanciar el bojeto CreditosDomain, se debe incluir la
	 * comision de apertura y corretaje y se debe calcular la tabla de los
	 * movimientos. Este método hace uso de todos los parámetros generados por
	 * los mencionados anteriormente.
	 * 
	 * <b>Edit:</b> 10.05.2017. Puesto que, tras hablar con Javier, la salida
	 * que se debe mostrar al hacer la simulación de liquidación ha de ser de
	 * una determinada forma, distinta a lo que hay ahora, este método pasa a
	 * devolver una lista de todos los cálculos a mostrar. El orden de lo
	 * cálculos dentro de la lista, su índice, es siempre el mismo:
	 * <ul>
	 * <li>0 - Intereses deudores</li>
	 * <li>1 - Intereses excedidos</li>
	 * <li>2 - Intereses acreedores</li>
	 * <li>3 - CSMND</li>
	 * <li>4 - Total días</li>
	 * <li>5 - Liquidacion total</li>
	 * </ul>
	 * 
	 * @return La liquidación final de la cuenta junto a varios parámetros que
	 *         se han usado para obtener ésta.
	 */
	public List<Double> obtenerLiquidacion() {
		double interesesDeudores = 0;
		double interesesExcedidos = 0;
		double interesesAcreedores = 0;
		double comisionSaldoMedioNoDispuesto;
		double interesesDeudoresFinales;
		double interesesAcreedoresFinales;
		double interesesExcedidosFinales;
		double liquidacionTotal;
		
		int totalDias = 0;

		for (Double interes : numerosDeudores) {
			interesesDeudores += interes;
		}

		for (Double interes : numerosAcreedores) {
			interesesAcreedores += interes;
		}

		for (Double interes : numerosExcedidos) {
			interesesExcedidos += interes;
		}

		for (Integer numeroDias : dias) {
			totalDias += numeroDias;
		}

		comisionSaldoMedioNoDispuesto = (this.limiteCredito - (interesesDeudores / totalDias))
				* this.comisionSMND;
		interesesDeudoresFinales = interesesDeudores * this.interesDeudor / 360;
		interesesAcreedoresFinales = interesesAcreedores * this.interesAcreedor / 360;
		interesesExcedidosFinales = interesesExcedidos * this.interesExcedido / 360;
		
		/* Atención. Los intereses acreedores finales se deben restar */
		liquidacionTotal = redondear(interesesDeudoresFinales - interesesAcreedoresFinales
				+ interesesExcedidosFinales + comisionSaldoMedioNoDispuesto);

		List<Double> listaResultados = new ArrayList<>();
		listaResultados.add(redondear(interesesDeudores));
		listaResultados.add(redondear(interesesExcedidos));
		listaResultados.add(redondear(interesesAcreedores));
		listaResultados.add(redondear(comisionSaldoMedioNoDispuesto));
		listaResultados.add((double) totalDias);
		listaResultados.add(redondear(liquidacionTotal));

		return listaResultados;
	}

	private void ordenarMovimientosPorFecha() {
		Collections.sort(this.movimientos, new Comparator<MovimientosCreditosDomain>() {
			@Override
			public int compare(MovimientosCreditosDomain m1, MovimientosCreditosDomain m2) {
				return m1.getFechaMovimiento().compareTo(m2.getFechaMovimiento());
			}
		});
	}

	private void cambiarSignoImportes() {
		for (MovimientosCreditosDomain movimiento : movimientos) {
			if (movimiento.getOperacion().equals(INGRESO)) {
				movimiento.setImporteMovimiento(-(movimiento.getImporteMovimiento()));
			}
		}
	}

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public double getInteresDeudor() {
		return interesDeudor;
	}

	public void setInteresDeudor(double interesDeudor) {
		this.interesDeudor = interesDeudor / 100.0;
	}

	public double getInteresExcedido() {
		return interesExcedido;
	}

	public void setInteresExcedido(double interesExcedido) {
		this.interesExcedido = interesExcedido / 100.0;
	}

	public double getInteresAcreedor() {
		return interesAcreedor;
	}

	public void setInteresAcreedor(double interesAcreedor) {
		this.interesAcreedor = interesAcreedor / 100.0;
	}

	public double getComisionSMND() {
		return comisionSMND;
	}

	public void setComisionSMND(double comisionSMND) {
		this.comisionSMND = comisionSMND / 1000.0;
	}

	public List<MovimientosCreditosDomain> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientosCreditosDomain> movimientos) {
		this.movimientos = movimientos;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
}
