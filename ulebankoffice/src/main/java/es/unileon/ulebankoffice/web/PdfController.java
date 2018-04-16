package es.unileon.ulebankoffice.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.unileon.ulebankoffice.domain.ClienteDomain;
import es.unileon.ulebankoffice.domain.CuentaCorrienteDomain;
import es.unileon.ulebankoffice.domain.DireccionDomain;
import es.unileon.ulebankoffice.repository.ClienteRepository;
import es.unileon.ulebankoffice.repository.CuentaCorrienteRepository;
import es.unileon.ulebankoffice.repository.DireccionRepository;

@Controller
@RequestMapping("/o/u")
public class PdfController {

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private DireccionRepository direccionesRepo;

	@Autowired
	private CuentaCorrienteRepository cuentasCorrientesRepo;
	
	private static final Logger logger = Logger.getLogger("ulebankLogger");
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static Font boldUnderlined = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	
	@GetMapping(value = "/contratoCuenta", params = { "accn" })
	public void inspeccionarContrato(HttpServletRequest req, HttpServletResponse response, Principal principal,
			@RequestParam("accn") String numeroDeCuenta) throws DocumentException, IOException {
		CuentaCorrienteDomain cuenta = cuentasCorrientesRepo.findByNumeroDeCuenta(numeroDeCuenta);
		if (cuenta == null) {
			throw new IllegalArgumentException("Numero de cuenta icnorrecto");
		}

		ClienteDomain cliente = clienteRepo.findByDni(cuenta.getDni().toString());
		if (cliente == null) {
			throw new IllegalArgumentException("Dni asociado a cuenta corriente incorrecto");
		}

		List<DireccionDomain> direccion = direccionesRepo.findByDni(cuenta.getDni().toString());
		if (direccion.isEmpty()) {
			throw new IllegalArgumentException("El cliente no tiene domicilio?");
		}
		
		DireccionDomain direccionCliente = direccion.get(0);
		
		Document document = new Document();
		logger.info(principal.getName() + " " + req.getRemoteAddr() + " quiere inspeccionar el contrato de la cuenta " + cuenta.getNumeroDeCuenta());
		
		String path = req.getSession().getServletContext().getRealPath("/resources/template/images");
		logger.debug(path);
		
		PdfWriter.getInstance(document,
				response.getOutputStream());
			
			document.open();
			DateTime dt;
			Chunk chunk;

			document.addCreator("ULeBank");
			document.addTitle("Contrato cuenta corriente");
			
			Image img = Image.getInstance( path +"/logo.png" );
			img.scaleAbsolute(250, 50);
			img.setAlignment(Image.ALIGN_CENTER);

			document.add(img);
			
			Paragraph titulo = new Paragraph();
			titulo.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(titulo, 1);
			titulo.add(new Paragraph("Contrato de la cuenta corriente " + cuenta.getNumeroDeCuenta(), catFont));
			addEmptyLine(titulo, 1);
			document.add(titulo);
			
			dt = new DateTime(cuenta.getFechaApertura());
			String fechaLugarContrato = "León, " + dt.getDayOfMonth() + " del " + dt.getMonthOfYear() + " de " + dt.getYear();
			document.add(new Paragraph(fechaLugarContrato));
			
			Paragraph p1 = new Paragraph("En el lugar y fecha expresados, reunidos, de una parte , la entidad ULeBank, con"
					+ " domicilio social en Campus de Vegazana, S/N, León inscrita en el Registro Mercantil de "
					+ "Castilla y León Tomo 00000, Folio 1, Hoja B-0000 con NIF A00000000,dirección de correo electrónico: ulebank@unileon.es, a partir de"
					+ " ahora el Banco, representado suficientemente por el apoderado o por los apoderados que "
					+ "suscriben el presente contrato, y por otra, los denominados Titulares (aunque sólo haya "
					+ "uno) que se identificarán más adelante.");
			p1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p1);

			Paragraph p2 = new Paragraph("El Banco es una entidad de crédito sujeta a la supervisión del "
					+ "Banco de España e inscrita en el registro administrativo especial con el número 0000."
					+ " La sede del Banco de España está en Madrid, en la calle Alcalá, 48, 28014 Madrid. "
					+ "La dirección de Internet es http://ulebank.unileon.es/");
			p2.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p2, 1);
			document.add(p2);

			Paragraph p3 = new Paragraph(
					"El Banco está adherido al sistema español de garantía de depósitos en entidades de crédito, en los términos del Real Decreto Ley 16/2011, de 14 de octubre, por el que se crea el Fondo de Garantía de Depósitos de Entidades de Crédito y su normativa de desarrollo. La cantidad máxima garantizada actualmente por dicho fondo es de 100.000 euros por depositante en cada entidad de crédito. Esta cantidad podrá sufrir variaciones en función de la normativa vigente en cada momento. El Banco está adherido a los Principios comunes para el traslado de cuentas bancarias, diseñados para su aplicación a nivel comunitario por el Comité Europeo de Industria Bancaria (EBIC).");
			p3.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p3, 1);
			document.add(p3);

			Paragraph p4 = new Paragraph(
					"Ambas partes se reconocen mutuamente capacidad para contratar y obligarse, y respecto de la cuenta indicada en las condiciones particulares, formalizan el presente contrato de apertura de cuenta o, si ya está abierta, de sustitución del contrato existente anteriormente de acuerdo con las condiciones siguientes:");
			p4.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p4, 1);
			document.add(p4);
			
			Paragraph p45 = new Paragraph();
			chunk = new Chunk("\nDatos del titular\n", boldUnderlined);
			p45.add(chunk);
			document.add(p45);
			
			PdfPTable tablaTitular = new PdfPTable(2);
			PdfPCell cell;
			chunk = new Chunk("Nombre:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cliente.getName());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Apellidos:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cliente.getLastName());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("DNI/NIE:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cliente.getDni().toString()+"\n");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Email:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cliente.getEmail());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Fecha nacimiento:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			dt = new DateTime(cliente.getFechaNacimiento());
			chunk = new Chunk(dt.getDayOfMonth() +" - " + dt.getMonthOfYear() + " - " + dt.getYear());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Nacionalidad:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cliente.getNacionalidad());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			document.add(tablaTitular);
			
			tablaTitular = new PdfPTable(2);
			document.newPage();
			p45 = new Paragraph();
			chunk = new Chunk("\nCon domicilio\n", boldUnderlined);
			p45.add(chunk);
			document.add(p45);
			
			chunk = new Chunk("Comunidad autónoma", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(direccionCliente.getComunidadAutonoma());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Localidad", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(direccionCliente.getLocalidad()+"\n");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Calle:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(direccionCliente.getCalle());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Número:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(direccionCliente.getNumero());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Código postal:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(	direccionCliente.getCodigoPostal()+"\n");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			document.add(tablaTitular);
			
			
			
			tablaTitular = new PdfPTable(2);

			Paragraph p5 = new Paragraph("\n");
			chunk = new Chunk("Datos y condiciones de la cuenta\n", boldUnderlined);
			p5.add(chunk);
			document.add(p5);
			
			chunk = new Chunk("Número de la cuenta:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cuenta.getNumeroDeCuenta());
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Intereses acreedores:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			
			chunk = new Chunk(cuenta.getInteresesAcreedores() + "%");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Intereses deudores:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cuenta.getInteresDeudorSobreSaldosNegativos() + "%");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Retención de rendimientos:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cuenta.getRetencionRendimientosCapital() + "%");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Comisión descubierto:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cuenta.getComisionDescubierto().toString()+ "%");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Mínimo:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cuenta.getMinimoComisionDescubierto().toString() + "€");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk("Comisión mantenimiento:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(cuenta.getComisionMantenimiento().toString() + "€");
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);

			
			String periodoLiquidacion;
			switch (cuenta.getPeriodoLiquidacion()) {
			case 1:
				periodoLiquidacion = "Mensual";
				break;
			case 3:
				periodoLiquidacion = "Trimestral";
				break;
			case 6:
				periodoLiquidacion = "Semestral";
				break;
			default:
				periodoLiquidacion = "Anual";
				break;
			}
			
			chunk = new Chunk("Periodo liquidación:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(periodoLiquidacion);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			
			chunk = new Chunk("Lugar y fecha de formalización:", bold);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);
			chunk = new Chunk(fechaLugarContrato);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			tablaTitular.addCell(cell);


			document.add(tablaTitular);
			
			chunk = new Chunk("Condiciones específicas de la Cuenta Corriente", boldUnderlined);
			document.add(chunk);
			Paragraph p6 = new Paragraph("En el caso de la Cuenta Primera, los Titulares y sus representantes cuando los Titulares cumplan 14 años y, para el caso de Cuenta Proyección, cuando los Titulares de la cuenta cumplan 26 años, excepto si existen pactos específicos, deberán presentarse en las oficinas del Banco contratante para formalizar un contrato nuevo y pactar las nuevas condiciones que se aplicarán a partir de ese momento."
					+ "Si no se formaliza un contrato nuevo, esta cuenta pasará a la modalidad de cuenta corriente o de ahorro bajo las condiciones estándar que el Banco tenga vigentes para este tipo de contratos en ese momento.");
			p6.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p6, 1);
			document.add(p6);

			chunk = new Chunk("Autorizaciones.", boldUnderlined);
			document.add(chunk);
			Paragraph p7 = new Paragraph("• Los Titulares que no deseen que sus datos personales sean tratados conforme a lo previsto en el apartado D,9.4: Tratamiento y cesión de datos con otras finalidades de la cláusula D.9. de las Condiciones Generales modelo ref. BS06/14CMI"
					+ " sobre régimen de protección de datos de carácter personal, bastará que manifiesten su negativa indicando su nombre y "
					+ " apellidos en el espacio previsto a continuación:");
			p7.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p7, 1);
			document.add(p7);
			
			chunk = new Chunk("Condiciones generales.", boldUnderlined);
			document.add(chunk);
			Paragraph p8 = new Paragraph( "El presente contrato incorpora condiciones generales predispuestas al margen de este documento las cuáles forman parte integrante de este contrato a todos los efectos y han sido conocidas y aceptadas por las partes. Concretamente son las Condiciones Generales Específicas del Contrato de Cuenta a la Vista, y las Condiciones Generales Comunes a los contratos de cuenta, tarjetas y servicios de banca a distancia y avisos-BS MÓVIL que figuran en los epígrafes A y D de las Condiciones Generales modelo ref. BS06/14CMI del Banco, que se hallan incorporadas al acta notarial de fecha 07 de marzo de 2014, autorizada por el Notario de Sabadell Don Javier Micó Giner con el número de protocolo 2785/2014, de la que puede obtenerse copia por parte de cualquier interesado. Asimismo el texto de las Condiciones Generales modelo BS06/14CMI está disponible de manera permanente para los Titulares en el sitio web del Banco en el respectivo apartado mediante enlace administrado"
					+ "Servicios de Tercero de Confianza, S.L. con CIF B-84492891 y domicilio en Madrid, c/ Hermosilla, 3, quien actúa en calidad de"
					+ "f tercero de confianza en documentos y transacciones electrónicas."
					+ "Los Titulares con la firma del presente contrato declaran bajo su responsabilidad que todos los datos contenidos en este"
					+ "documento son absolutamente ciertos. Asimismo, los Titulares autorizan al Banco a realizar aquellas comprobaciones que"
					+ " estime oportunas a fin de aseverar los datos anteriormente relacionados, comprometiéndose expresamente a facilitar ál"
					+ " 11 Banco, y siempre que éste lo requiera, todos aquellos documentos que justifiquen los datos facilitados por los Titulares al Banco.");
			p8.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p8, 1);
			document.add(p8);

			
			chunk = new Chunk("Explicaciones adecuadas.", boldUnderlined);
			document.add(chunk);
			Paragraph p9 = new Paragraph("El Banco informa a los Titulares de su derecho a solicitar cuántas explicaciones previas precisen sobre las características del producto objeto del contrato y sus efectos, incluidas las consecuencias en caso de impago, así como cuántas informaciones complementarias requieran para poder adoptar una decisión informada y poder comparar ofertas similares y evaluar la adecuación del producto a sus necesidades e intereses. "
					+ "Asimismo los Titulares tienen a su disposición en las oficinas y en la Web del Banco, la Información trimestral sobre comisiones y tipos practicados u ofertados de manera más habitual en las operaciones más frecuentes con los perfiles de clientes más comunes que sean personas físicas (Anejo 1de la Circular 5/2012 del Banco de España), al objeto de que puedan consultarlo. "
					+ "Por eso el Banco informa a los Titulares que sólo deberán firmar el presente documento en el caso de considerar haber"
					+ "obtenido las explicaciones suficientes del producto contratado y adecuadas a sus intereses, necesidades y situación financiera.");
			p9.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p9, 1);
			document.add(p9);

			PdfPTable table1 = new PdfPTable(2);
			PdfPCell cell1 = new PdfPCell(new Phrase("Leído y conforme, el/los Titular/es"));
			PdfPCell cell11 = new PdfPCell(new Phrase("ULeBank, Universidad de León"));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell11.setBorder(Rectangle.NO_BORDER);
			table1.addCell(cell1);
			table1.addCell(cell11);
			document.add(table1);
			
			document.newPage();

			Paragraph p10 = new Paragraph(
					"Los Titulares declaran conocer y aceptar las condiciones que figuran en el presente documento, así como la Incorporación en el mismo de las referidas condiciones generales específicas del contrato de cuenta a la vista y las condiciones generales comunes a los contratos de cuenta, tarjetas y servicios de banca a distancia y avisos- BS Móvil, y reconocen haber recibido el documento de Información previa y haber dispuesto de todo ello con antelación suficiente para su lectura y comprensión de su contenido."
							+ "En prueba de conformidad, firman las partes el presente contrato por duplicado ejemplar y a un solo efecto, en el lugar y fecha al principio Indicados, y en cumplimiento de lo establecido en la Circular 5/2012, de 27 de junio, del Banco de España, sobre transparencia de los servicios bancarios, tras la firma del presente contrato se entrega un ejemplar del mismo al cliente, sirviendo la propia firma del documento de recibí por parte de los Titulares.");
			p10.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p10, 1);
			document.add(p10);

			document.add(table1);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			Paragraph p11 = new Paragraph("");
			p11.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p11, 1);
			document.add(p11);

			Paragraph p12 = new Paragraph("");
			p12.setAlignment(Element.ALIGN_JUSTIFIED);
			addEmptyLine(p12, 1);
			document.add(p12);

			document.add(new Paragraph(""));
			
			
			img = Image.getInstance(path + "/contratoPDF/1.png");
			img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			img.setAlignment(Image.ALIGN_CENTER);

			document.add(img);
			img = Image.getInstance(path + "/contratoPDF/2.png");
			img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			img.setAlignment(Image.ALIGN_CENTER);

			document.add(img);
			img = Image.getInstance(path + "/contratoPDF/3.png");
			img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			img.setAlignment(Image.ALIGN_CENTER);

			document.add(img);
			img = Image.getInstance(path + "/contratoPDF/4.png");
			img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			img.setAlignment(Image.ALIGN_CENTER);

			document.add(img);

			document.close();
			logger.debug("Ha cargado el contrato de la cuenta " + cuenta.getNumeroDeCuenta());
		
		
		
		
		
		logger.info(principal.getName() + " " + req.getRemoteAddr() + " ha cargado el contrato de la cuenta " + cuenta.getNumeroDeCuenta());
	}
		
		private static void addEmptyLine(Paragraph paragraph, int number) {
			for (int i = 0; i < number; i++) {
				paragraph.add(new Paragraph(" "));
			}
		}
}
