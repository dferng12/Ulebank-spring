/**
 * 
 */
package es.unileon.ulebankoffice.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.unileon.ulebankoffice.domain.ClienteDomain;
import es.unileon.ulebankoffice.domain.CuentaCorrienteDomain;
import es.unileon.ulebankoffice.domain.DNIException;
import es.unileon.ulebankoffice.domain.DNIHandler;
import es.unileon.ulebankoffice.domain.DireccionDomain;
import es.unileon.ulebankoffice.domain.MovimientoCuentaCorrienteDomain;
import es.unileon.ulebankoffice.repository.ClienteRepository;
import es.unileon.ulebankoffice.repository.CuentaCorrienteRepository;
import es.unileon.ulebankoffice.repository.DireccionRepository;
import es.unileon.ulebankoffice.service.CuentaCorriente;
import es.unileon.ulebankoffice.service.CuentaCorrienteMovimientosAuxiliar;
import es.unileon.ulebankoffice.service.Direccion;
import es.unileon.ulebankoffice.service.MovimientoCuentaCorriente;

/**
 * @author Razvi Razvan Raducu
 *
 */
@Controller
@RequestMapping("/o/u")
public class OfficeClientPageController {

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private DireccionRepository direccionesRepo;

	@Autowired
	private CuentaCorrienteRepository cuentasCorrientesRepo;

	private String dniCliente;
	private String numeroDeCuenta;

	private static final Logger logger = Logger.getLogger("ulebankLogger");
	private static final String OFFICECLIENTPAGEVIEW = "officeclientpage";

	@ModelAttribute("nuevaCuentaCorriente")
	public CuentaCorriente cuentaCorriente() {
		return new CuentaCorriente();
	}

	@ModelAttribute("nuevaDireccion")
	public Direccion direccion() {
		return new Direccion();
	}

	@ModelAttribute("cuentaAuxiliar")
	public CuentaCorrienteMovimientosAuxiliar cuentaAuxiliar() {
		return new CuentaCorrienteMovimientosAuxiliar();
	}

	@RequestMapping(method = RequestMethod.GET, params = { "uin" })
	public String showClientData(ModelMap model, @RequestParam("uin") String dni) {

		ClienteDomain clienteEncontrado = clienteRepo.findByDni(dni);

		dniCliente = clienteEncontrado.getDni().toString();

		model.addAttribute("cliente", clienteEncontrado);
		model.addAttribute("direcciones", direccionesRepo.findByDni(dniCliente));
		model.addAttribute("cuentasCorrientes", cuentasCorrientesRepo.findByDni(dniCliente));
		logger.debug("El dni es -> : " + dniCliente);
		return OFFICECLIENTPAGEVIEW;
	}

	@PostMapping(value = "/nd")
	public String nuevaDireccion(ModelMap model, HttpServletRequest req, Principal principal,
			@Valid @ModelAttribute("nuevaDireccion") Direccion nuevaDireccion, BindingResult direccionResult)
			throws DNIException {
		logger.debug("El dni es -> : " + dniCliente);
		if (direccionResult.hasErrors()) {
			return OFFICECLIENTPAGEVIEW;
		}

		DireccionDomain direccion = new DireccionDomain(dniCliente, nuevaDireccion.getCalle(),
				nuevaDireccion.getLocalidad(), nuevaDireccion.getComunidadAutonoma(), nuevaDireccion.getCodigoPostal(),
				nuevaDireccion.getNumero());

		direccionesRepo.save(direccion);
		logger.info(principal.getName() + " " + req.getRemoteAddr()
				+ " ha dado de alta una nueva dirección para el cliente: " + dniCliente);
		return "redirect:/o/u?uin=" + dniCliente;

	}

	@PostMapping(value = "/nc")
	public String nuevaCuentaCorriente(ModelMap model, HttpServletRequest req, Principal principal,
			@Valid @ModelAttribute("nuevaCuentaCorriente") CuentaCorriente nuevaCuenta, BindingResult cuentaResult)
			throws DNIException {

		if (cuentaResult.hasErrors()) {
			return OFFICECLIENTPAGEVIEW;
		}

		CuentaCorrienteDomain cuenta = new CuentaCorrienteDomain(new Date(), nuevaCuenta.getInteresesAcreedores(),
				nuevaCuenta.getInteresDeudorSobreSaldosNegativos(), nuevaCuenta.getRetencionRendimientosCapital(),
				nuevaCuenta.getComisionMantenimiento(), nuevaCuenta.getComisionDescubierto(),
				nuevaCuenta.getMinimoComisionDescubierto(), null, 0.0, "Abierta", new DNIHandler(dniCliente),
				nuevaCuenta.getDiasAnuales(), nuevaCuenta.getPeriodoLiquidacion());

		cuenta.setNumeroDeCuenta(
				StringUtils.leftPad(Integer.toString(cuentasCorrientesRepo.findAll().size() + 1), 10, '0'));

		cuentasCorrientesRepo.save(cuenta);
		logger.info(principal.getName() + " " + req.getRemoteAddr()
				+ " ha dado de alta una nueva cuenta corriente para el cliente: " + dniCliente);
		return "redirect:/o/u?uin=" + dniCliente;
	}

	@GetMapping(value = "/c")
	public String viewAccount(ModelMap model, HttpServletRequest req, Principal principal,
			@RequestParam("accn") String numeroCuenta) {
		/* Si la cuenta no existe, se devuelve 404 */

		CuentaCorrienteDomain cuenta = cuentasCorrientesRepo.findByNumeroDeCuenta(numeroCuenta);
		if (cuenta == null) {
			logger.warn(principal.getName() + " " + req.getRemoteAddr()
					+ " ha tratado de inspeccionar un número de cuenta -> " + numeroCuenta
					+ " que no existe. Devolviendo 404.");
			return "redirect:/e/404";
		}

		model.addAttribute("cuentaAuxiliar", new CuentaCorrienteMovimientosAuxiliar());
		model.addAttribute("cuenta", cuenta);
		numeroDeCuenta = cuenta.getNumeroDeCuenta();
		return "currentaccount";
	}

	@PostMapping(value = "/c")
	public String registerMovements(ModelMap model, HttpServletRequest req, Principal principal,
			@Valid @ModelAttribute("cuentaAuxiliar") CuentaCorrienteMovimientosAuxiliar movimientosNuevaCuenta,
			BindingResult movimientosResults) {

		if (movimientosResults.hasErrors()) {
			logger.info(principal.getName() + " " + req.getRemoteAddr()
					+ " ha tratado de registrar unos movimientos para la cuenta: " + numeroDeCuenta
					+ " que contenían errores.");
			return "currentaccount";
		}

		CuentaCorrienteDomain cuentaCorriente = cuentasCorrientesRepo.findByNumeroDeCuenta(numeroDeCuenta);
		MovimientoCuentaCorrienteDomain nuevoMovimiento;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yy-MM-dd");
		DateTime fechaMovimiento;
		double saldoAux = 0.0;

		/*
		 * Se comprueba si cuentaCorriente tiene inicializada la lsita de
		 * movimientos para evitar el nullPointer
		 */
		if (cuentaCorriente.getMovimientos() == null) {
			cuentaCorriente.setMovimientos(new ArrayList<MovimientoCuentaCorrienteDomain>());
		}

		for (MovimientoCuentaCorriente movimiento : movimientosNuevaCuenta.getMovimientos()) {

			fechaMovimiento = formatter.parseDateTime(movimiento.getFechaValor());
			nuevoMovimiento = new MovimientoCuentaCorrienteDomain(movimiento.getImporte(), movimiento.getConcepto(),
					fechaMovimiento.toDate(), movimiento.getOperacion());

			saldoAux = "D".equals(movimiento.getOperacion()) ? saldoAux - movimiento.getImporte()
					: saldoAux + movimiento.getImporte();

			cuentaCorriente.addMovimiento(nuevoMovimiento);
		}

		logger.info(principal.getName() + " " + req.getRemoteAddr() + " ha añadido movimientos a la cuenta: "
				+ numeroDeCuenta);
		cuentaCorriente.setSaldo(BigDecimal.valueOf(Math.rint(cuentaCorriente.getSaldo() + saldoAux * 100) / 100)
				.setScale(2, RoundingMode.HALF_UP).doubleValue());
		cuentasCorrientesRepo.save(cuentaCorriente);

		return "redirect:/o/u/c?accn=" + numeroDeCuenta;
	}

	@PostMapping(value = "/c", params = { "fechaInicioLiquidacion", "fechaFinalLiquidacion" })
	public String liquidar(ModelMap model, HttpServletRequest req, Principal principal,
			@RequestParam("fechaInicioLiquidacion") String fechaInicioLiquidacion,
			@RequestParam("fechaFinalLiquidacion") String fechaFinalLiquidacion, HttpServletResponse response) {
		logger.debug("He captado el subnmit de la liquidación. Con fecha incio -> " + fechaInicioLiquidacion
				+ " y fecha final -> " + fechaFinalLiquidacion);

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yy-MM-dd");
		DateTime fechaInicio = formatter.parseDateTime(fechaInicioLiquidacion);
		DateTime fechaFin = formatter.parseDateTime(fechaFinalLiquidacion);

		CuentaCorrienteDomain cuenta = cuentasCorrientesRepo.findByNumeroDeCuenta(numeroDeCuenta);

		logger.info(principal.getName() + " " + req.getRemoteAddr() + "ha realizado la liquidación de la cuenta "
				+ cuenta.getNumeroDeCuenta());

		model.addAttribute("tabla", cuenta.realizarLiquidacion(fechaInicio.toDate(), fechaFin.toDate()));

		model.addAttribute("cuenta", cuenta);

		cuentasCorrientesRepo.save(cuenta);

		return "accountliquidation";
	}

}
