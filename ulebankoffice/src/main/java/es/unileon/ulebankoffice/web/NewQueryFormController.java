package es.unileon.ulebankoffice.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import es.unileon.ulebankoffice.domain.AdvisorUserDomain;
import es.unileon.ulebankoffice.domain.SolicitudFinancialAdvisorDomain;
import es.unileon.ulebankoffice.repository.AdvisorUserRepository;
import es.unileon.ulebankoffice.repository.SolicitudesFinancialAdvisorRepository;
import es.unileon.ulebankoffice.service.SolicitudFinancialAdvisor;

@Controller
@RequestMapping("/offersconsulting/newquery")
public class NewQueryFormController {

	private static final Logger logger = Logger.getLogger("ulebankLogger");
	private static final String REDIRECTNEWQUERY = "redirect:/o/offersconsulting/newquery";
	private static final String FILEERROR = "fileError";
	private static final String UTF8 = "UTF-8";

	@Autowired
	private SolicitudesFinancialAdvisorRepository repo;
	
	@Autowired
	private AdvisorUserRepository repoUser;
	
	@Autowired
	private GridFsTemplate template;
	
	@ModelAttribute("newQuestion")
	public SolicitudFinancialAdvisor getNewQuestion() {
		return new SolicitudFinancialAdvisor();
	}

	@PostMapping
	public String processAdd(@Valid @ModelAttribute("newQuestion") SolicitudFinancialAdvisor nuevaSolicitud,
			BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res, Principal principal,
			ModelMap model) throws IOException {
		
		List<MultipartFile> multiFiles = nuevaSolicitud.getFiles();
		InputStream file = new BufferedInputStream(multiFiles.get(0).getInputStream());
		logger.info("El usuario ha subido " + multiFiles.size() + " archivos.");
		
		String id[] = new String[]{null};
        //El codigo actual se implementa en vistas a subir mas de un archivo 
        if (null != multiFiles && multiFiles.size() > 0) {
        	int j = 0;
        	id = new String[multiFiles.size()];
            for (MultipartFile multipartFile : multiFiles) {
            	if(!multipartFile.isEmpty()) {
            		
            		if(!"application/pdf".equals(multipartFile.getContentType())) {
            			logger.warn("Se ha tratado de adjuntar un archivo con contenido distinto a PDF. Borrando archivo.");
        				model.addAttribute(FILEERROR, "Must be PDF!");
        				return REDIRECTNEWQUERY;
            		}
            		
            		byte fileBytes[] = multipartFile.getBytes();
            		byte[] bytesPDF = { 37, 80, 68, 70 };
            		for(int i = 0; i < 4; i++) {
            			if(fileBytes[i] != bytesPDF[i]) {
            				logger.warn(
            						"Se ha tratado de adjuntar un archivo cuyos 4 primeros bytes, números mágicos, no coinciden con los de PDF. Borrando archivo.");
            				model.addAttribute(FILEERROR, "PDF Content!");
            				return REDIRECTNEWQUERY;
            			}
            		}
        			logger.info("Contenido del archivo, numero mágicos,... OK");
        	        InputStream inputStream =  new BufferedInputStream(multipartFile.getInputStream());
        	        id[j] = template.store(inputStream, principal.getName()).getId().toString();
        	        j++;
            		
            	}
               
            }
        }
        
        String texto = nuevaSolicitud.getTextoOferta();
		String asunto = nuevaSolicitud.getAsuntoOferta();
		String url = nuevaSolicitud.getUrlOferta();
		String userEmail = principal.getName();
		SolicitudFinancialAdvisorDomain solicitud = new SolicitudFinancialAdvisorDomain();
		solicitud.setEmail(userEmail);
		solicitud.setEstado("Pendiente");
		//Por ahora se contempla solo un archivo
		solicitud.setFileBlobKey(id[0]);
		solicitud.setUrlOferta(url);
		solicitud.setTextoOferta(texto);
		solicitud.setAsuntoOferta(asunto);

		/* Se otiene la fecha de creación */
		LocalDateTime now = LocalDateTime.now();

		solicitud.setFechaCreacion(now.getDayOfMonth() + "-" + now.getMonthOfYear() + "-" + now.getYear());

		repo.save(solicitud);
		logger.info("Se ha guardado en la base de datos (MongoDB) la solicitud de " + userEmail + " con id: "
				+ solicitud.getId() + " y asunto : " + asunto);
		
		AdvisorUserDomain user = repoUser.findByEmail(userEmail);

		if (user.getResultadoTest() <= 0.0) {
			model.addAttribute("testPendiente", 1);
			model.addAttribute("userEmail", userEmail);
		} 
		
		return "question-verification";
	}
	
	@GetMapping
	public String add(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
		return "newquery";

	}
	
	
}
