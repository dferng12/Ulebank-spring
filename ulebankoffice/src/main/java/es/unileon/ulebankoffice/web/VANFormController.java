package es.unileon.ulebankoffice.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.unileon.ulebankoffice.service.VAN;

@Controller
public class VANFormController {
	
	@RequestMapping(value = "/van.htm", method = RequestMethod.POST)
    public ModelAndView processAdd(@ModelAttribute("van") VAN van, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors())
        	return new ModelAndView("van");
        
        int n = van.getN();
        double k = van.getK();
        String Qj = van.getQj();
        double Q0 = van.getQ0();
        
        String[] flujos = Qj.split(";");
        
        double resultadoParcial = 0;
        
        for(int j=1; j<=n; j++) {
        	double flujo = Double.parseDouble(flujos[j-1]);
        	resultadoParcial += flujo / Math.pow(1+k, j);
        }
        
        double resultado = resultadoParcial + Q0;
        
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("resultado", resultado);

        return new ModelAndView("van", "model", myModel);
    }

    @RequestMapping(value = "/van.htm", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("van", new VAN());
        
        return "van";
    }
}
