package es.unileon.ulebankoffice.web;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import es.unileon.ulebankoffice.domain.CreditosDomain;
import es.unileon.ulebankoffice.domain.MovimientosCreditosDomain;
import es.unileon.ulebankoffice.service.Creditos;
import es.unileon.ulebankoffice.service.MovimientosCreditos;

@Controller
public class CreditosFormController {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final String MONEDA = "€";

	@RequestMapping(value = "/creditaccount", method = RequestMethod.POST)
    public ModelAndView processAdd(@Valid @ModelAttribute("creditos")
    		Creditos creditos, BindingResult bindingResult, HttpServletResponse response) throws ParseException {
		
		ModelAndView model = new ModelAndView("creditaccount");
		
		if (bindingResult.hasErrors())
        	return model;
		
		List<MovimientosCreditos> movimientos = creditos.getMovimientos();
		
		List<MovimientosCreditosDomain> myMovimientos = new ArrayList<>();
		
		response.addCookie(new Cookie("movimientos", new Gson().toJson(movimientos)));
		response.addCookie(new Cookie("fechaApertura", creditos.getFechaApertura()));
		response.addCookie(new Cookie("fechaVencimiento", creditos.getFechaVencimiento()));
		
		String descripcionMovimiento;
		double importeMovimiento;
		Date fechaMovimiento;
		String operacion;
		DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00");
		decimalFormatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMAN));
	
		for(MovimientosCreditos movimiento : movimientos) {
			fechaMovimiento = sdf.parse(movimiento.getFechaMovimiento());
			descripcionMovimiento = movimiento.getDescripcionMovimiento();
			importeMovimiento = movimiento.getImporteMovimiento();
			operacion = movimiento.getOperacion();
			myMovimientos.add(new MovimientosCreditosDomain(descripcionMovimiento, importeMovimiento, fechaMovimiento,operacion));
		}
		
		double limiteCredito = creditos.getLimiteCredito();
		Date fechaApertura = sdf.parse(creditos.getFechaApertura());
		Date fechaVencimiento = sdf.parse(creditos.getFechaVencimiento());
		double interesDeudor = creditos.getInteresDeudor();
		double interesExcedido = creditos.getInteresExcedido();
		double interesAcreedor = creditos.getInteresAcreedor();
		double comisionSMND = creditos.getComisionSMND();
		double comisionApertura = creditos.getComisionApertura();
		double corretaje = creditos.getCorretaje();
		
		CreditosDomain myCreditos = new CreditosDomain(limiteCredito, fechaApertura, fechaVencimiento, interesDeudor, 
				interesExcedido, interesAcreedor, comisionSMND, myMovimientos);
		
		myCreditos.incluirComsionAperturaYCorretaje(comisionApertura, corretaje);
		
		List<List<String>> tabla = myCreditos.calcularTabla();
		List<Double> totalLiquidacion = myCreditos.obtenerLiquidacion();
		
		/* El último elemento de esta tabla es la Liquidación per se */
		List<String> itemTabla = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fechaVencimiento);
		
		double interesesDeudores = totalLiquidacion.get(0);
		double interesesExcedidos = totalLiquidacion.get(1);
		double interesesAcreedores = totalLiquidacion.get(2);
		
		String totalInteresesDeudores = decimalFormatter.format(interesesDeudores)+ MONEDA;
		String totalInteresesExcedidos = decimalFormatter.format(interesesExcedidos)+ MONEDA;
		String totalInteresesAcreedores = decimalFormatter.format(interesesAcreedores)+ MONEDA;
		String comisionSMNDtoString = decimalFormatter.format(totalLiquidacion.get(3))+ MONEDA;
		String totalLiquidacionToString = decimalFormatter.format(totalLiquidacion.get(5))+ MONEDA;
		String totalSaldo = tabla.get(tabla.size()-1).get(4);
		
		String totalSaldoToString = totalSaldo.substring(0, totalSaldo.length()-1);
		totalSaldoToString = totalSaldoToString.replaceAll("\\.", "");
		totalSaldoToString = totalSaldoToString.replaceAll(",", ".");
		
		Double saldoFinal = Double.parseDouble(totalSaldoToString);
		
		/* Atencion. Se suma 1 puesto que en el calendario Gregoriano las fechas empiezan en 0. Enero el es el mes 0 y Diciembre el 11. */
		itemTabla.add(calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1));
	
		itemTabla.add("Liquidación");
		itemTabla.add(totalLiquidacionToString);
		itemTabla.add("-");
		itemTabla.add(decimalFormatter.format(saldoFinal + totalLiquidacion.get(5)));
		itemTabla.add(String.valueOf(totalLiquidacion.get(4).intValue()));
		itemTabla.add(totalInteresesDeudores);
		itemTabla.add(totalInteresesExcedidos);
		itemTabla.add(totalInteresesAcreedores);
		
		tabla.add(itemTabla);
		
		
		
		model.addObject("tabla", tabla);
		
		/* Tabla de la Liquidación */
		
		
		model.addObject("iDeudores1",decimalFormatter.format(interesDeudor) + "%");
		model.addObject("iDeudores2",decimalFormatter.format(interesesDeudores*myCreditos.getInteresDeudor()/360)+ MONEDA);
		
		model.addObject("iExcedidos1",decimalFormatter.format(interesExcedido) + "%");
		model.addObject("iExcedidos2",decimalFormatter.format(interesesExcedidos*myCreditos.getInteresExcedido()/360)+ MONEDA);
		
		model.addObject("iAcreedores1",decimalFormatter.format(interesAcreedor) + "%");
		model.addObject("iAcreedores2",decimalFormatter.format(interesesAcreedores*myCreditos.getInteresAcreedor()/360)+ MONEDA);
		
		model.addObject("CSMND1",decimalFormatter.format(comisionSMND) + "%");
		model.addObject("CSMND2",comisionSMNDtoString);
		
		model.addObject("total", totalLiquidacionToString);
		
        response.addCookie(new Cookie("resultados", "1"));
        
        
        return model;
	}
	
	@RequestMapping(value = "/creditaccount", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute("creditos") Creditos creditos) {
        return "creditaccount";
    }
}
