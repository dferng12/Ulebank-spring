package es.unileon.ulebankoffice.web;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.unileon.ulebankoffice.domain.ClienteDomain;
import es.unileon.ulebankoffice.domain.CuentaCorrienteDomain;
import es.unileon.ulebankoffice.domain.DNIHandler;
import es.unileon.ulebankoffice.domain.DireccionDomain;
import es.unileon.ulebankoffice.domain.Documentos;
import es.unileon.ulebankoffice.domain.Handler;
import es.unileon.ulebankoffice.domain.UleBankEmployeeDomain;
import es.unileon.ulebankoffice.repository.ClienteRepository;
import es.unileon.ulebankoffice.repository.CuentaCorrienteRepository;
import es.unileon.ulebankoffice.repository.DireccionRepository;
import es.unileon.ulebankoffice.repository.UleBankEmployeeRepository;
import es.unileon.ulebankoffice.service.Cliente;
import es.unileon.ulebankoffice.service.ClienteCuentaCorrienteDireccion;
import es.unileon.ulebankoffice.service.CuentaCorriente;
import es.unileon.ulebankoffice.service.Direccion;
import es.unileon.ulebankoffice.service.UleBankEmployee;

/**
 * @author Razvan Raducu
 *
 */
@Controller
@RequestMapping(value = "/o")
public class OfficeIndexController {

	@Autowired
	private ClienteRepository repoClientes;

	@Autowired
	private CuentaCorrienteRepository repoCuentas;

	@Autowired
	private DireccionRepository repoDirecciones;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private UleBankEmployeeRepository employeesRepo;

	private static final Logger logger = Logger.getLogger("ulebankLogger");
	private static final String CLIENTSVIEWATTRIBUTE = "clients";
	private static final String CLIENTERROR = "clientError";
	private static final String OFFICEINDEXVIEW = "officeindex";
	private static final String REDIRECTOFFICE = "redirect:/o";

	@ModelAttribute("nuevoEmpleado")
	public UleBankEmployee addEmployee() {
		return new UleBankEmployee();
	}

	@ModelAttribute("nuevoCliente")
	public ClienteCuentaCorrienteDireccion addClient() {
		return new ClienteCuentaCorrienteDireccion();
	}

	@ModelAttribute(CLIENTSVIEWATTRIBUTE)
	public List<ClienteDomain> getClients() {
		return repoClientes.findAll();
	}

	@ModelAttribute("employees")
	public List<UleBankEmployeeDomain> getEmployees(HttpServletRequest req) {
		if (req.isUserInRole("ROLE_ADMIN")) {
			return employeesRepo.findAll();
		}
		return new ArrayList<>();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String comingSoon(ModelMap model, Principal principal, HttpServletRequest req) {
		logger.info(principal.getName() + " ha accedido a la página principal de la oficina.");
		return OFFICEINDEXVIEW;
	}

	@RequestMapping(method = RequestMethod.GET, params = "uin")
	public String showMeAclient(ModelMap model, @RequestParam("uin") String dni, HttpServletRequest req,
			Principal principal) {

		logger.info(principal.getName() + " " + req.getRemoteAddr() + " ha buscado el cliente con dni: " + dni);

		/*
		 * Es mucho más sensato capturar el idioma que el país ya que puede ser
		 * es_ES o es_MX. El idioma es el mismo pero el país no. De cara al
		 * futuro puede suponer problemas si se captura sólo el país.
		 */
		String locale = req.getLocale().getLanguage();
		String noClientFoundError = "";
		if (locale.equals(new Locale("en").getLanguage())) {
			noClientFoundError = "No client found with such identifier " + dni;
		} else if (locale.equals(new Locale("es").getLanguage())) {
			noClientFoundError = "No hay ningún cliente con el identificador " + dni;
		}

		ClienteDomain clienteFound = repoClientes.findByDni(dni);
		if (clienteFound == null) {
			logger.debug("el cliente no se ha encontrado y el locale es :" + locale);
			logger.info("Cliente no existente");
			model.addAttribute(CLIENTERROR, noClientFoundError);
			model.addAttribute(CLIENTSVIEWATTRIBUTE, repoClientes.findAll());
		} else {
			logger.info("Cliente encontrado");

			List<ClienteDomain> clienteEncontrado = new ArrayList<>();
			clienteEncontrado.add(clienteFound);

			model.addAttribute(CLIENTSVIEWATTRIBUTE, clienteEncontrado);
		}

		return OFFICEINDEXVIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveNewClient(@ModelAttribute("nuevoCliente") @Valid ClienteCuentaCorrienteDireccion nuevoCliente,
			BindingResult clienteResult, BindingResult empleadoResult, HttpServletRequest req, HttpServletResponse resp,
			ModelMap model, Principal principal) throws ParseException {

		Cliente clientePojo = nuevoCliente.getCliente();
		Direccion direccionPojo = nuevoCliente.getDireccion();
		CuentaCorriente cuentaPojo = nuevoCliente.getCuentaCorriente();

		ClienteDomain cliente;
		DireccionDomain direccion;
		CuentaCorrienteDomain cuentaCorriente;

		Handler DNI;

		logger.debug("LA FECHA DEL CLIENTE LLEGA COMO: ->> " + clientePojo.getFechaNacimiento());
		if (clienteResult.hasErrors()) {
			logger.warn(principal.getName() + " " + req.getRemoteAddr()
					+ " ha intentando crear un nuevo cliente y ha habido errores.");
			logger.debug("HA HABIDO ERRORES ->" + clienteResult.getAllErrors().toString());
			return OFFICEINDEXVIEW;
		}

		try {
			DNI = new DNIHandler(clientePojo.getDni());
		} catch (Exception e) {
			clienteResult.addError(new FieldError("nuevoCliente", "cliente.dni", e.getMessage()));
			logger.error(principal.getName() + " " + req.getRemoteAddr()
					+ " ha intentado crear un cliente y se ha producido un error: " + e.getMessage() + " ||\n " + e
					+ "||" + e.getLocalizedMessage());
			return OFFICEINDEXVIEW;
		}

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yy-MM-dd");
		DateTime fechaNacimientoCliente = formatter.parseDateTime(clientePojo.getFechaNacimiento());
		DateTime fechaActual = new DateTime();

		cliente = new ClienteDomain(clientePojo.getName(), clientePojo.getLastName(), clientePojo.getEmail(),
				fechaNacimientoCliente.toDate(), DNI, clientePojo.getNacionalidad(),
				new Documentos(new ArrayList<String>()), new Date());
		direccion = new DireccionDomain(direccionPojo.getCalle(), direccionPojo.getLocalidad(),
				direccionPojo.getComunidadAutonoma(), direccionPojo.getNumero(), DNI, direccionPojo.getCodigoPostal());

		cuentaCorriente = new CuentaCorrienteDomain(fechaActual.toDate(), cuentaPojo.getInteresesAcreedores(),
				cuentaPojo.getInteresDeudorSobreSaldosNegativos(), cuentaPojo.getRetencionRendimientosCapital(),
				cuentaPojo.getComisionMantenimiento(), cuentaPojo.getComisionDescubierto(),
				cuentaPojo.getMinimoComisionDescubierto(), new Date(), 0.0, "Abierta", DNI, cuentaPojo.getDiasAnuales(),
				cuentaPojo.getPeriodoLiquidacion());

		/*
		 * Gracias a StringUtils puedo obtener el número de cuentas totales y
		 * rellenarlo con ceros a la izquierda hasta los 10 caracteres
		 */
		cuentaCorriente
				.setNumeroDeCuenta(StringUtils.leftPad(Integer.toString(repoCuentas.findAll().size() + 1), 10, '0'));

		try {
			repoDirecciones.save(direccion);
			repoCuentas.save(cuentaCorriente);
			repoClientes.save(cliente);
		} catch (Exception e) {
			model.addAttribute(CLIENTERROR, "Ya existe un cliente con ese DNI/NIE");
			logger.error(principal.getName() + " " + req.getRemoteAddr()
					+ " ha intentado crear un cliente que ya existe (" + cliente.getDni().toString() + ")." + e);
			return OFFICEINDEXVIEW;
		}

		logger.info("Se ha creado el nuevo cliente con dni: " + cliente.getDni() + " e identificación en MognoDB: "
				+ cliente.getId());
		logger.info("Se ha creado la nueva cuenta corriente perteneciente al dni: " + cuentaCorriente.getDni()
				+ " e identificación en MognoDB: " + cuentaCorriente.getId());
		logger.info("Se ha creado la nueva dirección perteneciente al dni: " + direccion.getDni()
				+ " e identificación en MognoDB: " + direccion.getId());
		return "newClientVerification";

	}

	@RequestMapping(value = "/admin/submitEmployee", method = RequestMethod.POST)
	public String saveNewClient(@ModelAttribute("nuevoEmpleado") @Valid UleBankEmployee nuevoEmpleado,
			BindingResult empleadoResult, HttpServletRequest req, HttpServletResponse resp, ModelMap model,
			Principal principal) {

		if (empleadoResult.hasErrors()) {

			logger.warn("Se ha tratado de crear un empleado. Los datos son incorrectos."
					+ empleadoResult.getAllErrors().toString());
			return REDIRECTOFFICE;
		}

		UleBankEmployeeDomain empleado = new UleBankEmployeeDomain(nuevoEmpleado.getUserName(),
				bcrypt.encode(nuevoEmpleado.getPassword()), nuevoEmpleado.getRole());

		try {
			employeesRepo.save(empleado);
		} catch (Exception e) {
			model.addAttribute("errorRegistroEmpleado", "Ya existe un empleado con ese nombre de usuario");
			logger.info("Se ha tratado de crear el usuario de la sucursal:" + empleado.getUserName()
					+ " con privilegios: " + empleado.getRole() + " pero ya existía. " + e);
			return REDIRECTOFFICE;
		}

		logger.info(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
				+ " Ha creado el usuario de la sucursual: " + empleado.getUserName() + " con privilegios: "
				+ empleado.getRole());
		return REDIRECTOFFICE;

	}

	@RequestMapping(value = "/admin/d", method = RequestMethod.GET, params = "ein")
	public String deleteEmployee(ModelMap model, @RequestParam("ein") String employeeUserName, Principal principal,
			HttpServletRequest req) {

		if ("cjrulebank".equalsIgnoreCase(employeeUserName)) {
			logger.fatal(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
					+ " ha intentado borrar al usuario administrador.");
			return REDIRECTOFFICE;
		}

		UleBankEmployeeDomain empleado = employeesRepo.findByUserName(employeeUserName);

		/*
		 * Por si a alguien se le ocurre introducir la URL directamente en el
		 * navegador con un "ein" inexistente. Con esto se evita la excepción
		 * IllegalArgumentException del método .delete()
		 */
		if (empleado == null) {
			logger.warn(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
					+ " Ha tratado de borrar un empleado inexistente. Alguien ha accedido a una URL que no debía. Empleado inexistente: "
					+ employeeUserName);
			return REDIRECTOFFICE;
		}
		logger.info(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
				+ " Ha eliminado el empleado de la oficina: " + employeeUserName);
		employeesRepo.delete(empleado);
		return REDIRECTOFFICE;

	}

}
