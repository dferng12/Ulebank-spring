package es.unileon.ulebankoffice.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.gridfs.GridFSDBFile;

import es.unileon.ulebankoffice.domain.AdvisorUserDomain;
import es.unileon.ulebankoffice.domain.SolicitudFinancialAdvisorDomain;
import es.unileon.ulebankoffice.repository.AdvisorUserRepository;
import es.unileon.ulebankoffice.repository.SolicitudesFinancialAdvisorRepository;

@Controller
@RequestMapping(value = "/offersconsulting/querypage")
public class QueryPageController {

	@Autowired
	private GridFsTemplate template;
	
	@Autowired
	private SolicitudesFinancialAdvisorRepository repo;
	
	@Autowired
	private AdvisorUserRepository repoUser;

	private static final Logger logger = Logger.getLogger("ulebankLogger");

	@GetMapping(params = { "id" })
	public String add(ModelMap model, HttpServletRequest req, HttpServletResponse resp, Principal principal,
			@RequestParam("id") String idSolicitud) throws IOException {

		SolicitudFinancialAdvisorDomain solicitud = repo.findOne(idSolicitud);

		if (solicitud == null) {
			logger.warn(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
					+ " ha tratado de acceder a una consulta inexistente. Devolviendo 404 advisor.");
			return "redirect:/e/404";
		}

		/*
		 * Se comprueba que el usuario que está accediendo a la solicitud es el
		 * creador de la misma o bien un empleado de la oficina o superiores. Todos 
		 * los usuarios que no saen el administrador del sistema o los empleados
		 * de la banca tienen el rol ROLE_ADVISORUSER. Si tienen este rol y no son
		 * el creador de la consulta, se devuelve error.
		 */
		String creadorSolicitud = solicitud.getEmail();
		if (hasRole("ROLE_ADVISORUSER") && !principal.getName().equals(creadorSolicitud)) {
			logger.warn(
					"El usuario " + principal.getName() + req.getRemoteAddr() + " ha intentado acceder a la consulta "
							+ solicitud.getId() + " del usuario " + creadorSolicitud + ". Redireccionando a 403.");
			return "redirect:/e/403";
		}

		/*
		 * Si es empleado, podrá ver los comandos para añadir nueva respuesta.
		 * Si la solicitud ya tiene una respuesta, se le cargará en el text área
		 * por si lo que quiere es modificarla.
		 */
		
		String address = solicitud.getFileBlobKey() == null ? null : solicitud.getId();
		
		model.addAttribute("asuntoOferta", solicitud.getAsuntoOferta());
		model.addAttribute("idQuery", solicitud.getId());
		model.addAttribute("enlaceArchivo", "/offersconsulting/querypage/serve?id=" + address);		
		model.addAttribute("textoOferta", solicitud.getTextoOferta());
		model.addAttribute("urlOferta", solicitud.getUrlOferta());
		model.addAttribute("respuestaOferta", solicitud.getRespuestaOferta());
		model.addAttribute("autorConsulta", creadorSolicitud);
		model.addAttribute("fechaCreacion", solicitud.getFechaCreacion());
		
		AdvisorUserDomain user = repoUser.findByEmail(creadorSolicitud);
		model.addAttribute("testScore", user.getResultadoTest());

		return "querypage";

	}

	@PostMapping(params = { "id" })
	public String addResponse(ModelMap model, @RequestParam("response") String respuestaSolicitud,
			@RequestParam("id") String idSolicitud, @RequestParam("scoreTest") double scoreTest, Principal principal, HttpServletRequest req) {
		if (hasRole("ROLE_ADMIN") || hasRole("ROLE_SUPERVISOR") || hasRole("ROLE_EMPLEADO")) {
			SolicitudFinancialAdvisorDomain solicitud = repo.findOne(idSolicitud);

			if (solicitud == null) {
				logger.warn(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
						+ " ha tratado de acceder a una consulta inexistente. Devolviendo 404 advisor.");
				return "redirect:/e/404";
			}

			solicitud.setRespuestaOferta(respuestaSolicitud);
			solicitud.setEstado("Contestada");
			
			AdvisorUserDomain user = repoUser.findByEmail(solicitud.getEmail());
			user.setResultadoTest(scoreTest);
			
			
			repoUser.save(user);
			repo.save(solicitud);
			logger.info(principal.getName() + " ha añadido una respuesta a la consulta " + solicitud.getId());
			return "redirect:/offersconsulting/querypage?id=" + idSolicitud;
		} else {
			logger.error(req.getRemoteAddr() + " " + req.getLocalAddr()
					+ " Alguien ha tratado de hacer POST a una id de una consulta sin tener los roles necesarios o haber iniciado sesión. Esto significa que alguien ha tratado, por medios externos, contestar a una query.");
			return "redirect:/e/403";
		}

	}

	@GetMapping(value = "/e", params = { "id" })
	public String eliminarConsulta(ModelMap model, HttpServletRequest req, HttpServletResponse resp,
			Principal principal, @RequestParam("id") String idSolicitud) {
		SolicitudFinancialAdvisorDomain solicitud = repo.findOne(idSolicitud);

		logger.info(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
				+ "está tratando de borrar una consulta.");

		if (solicitud == null) {
			logger.warn(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
					+ " ha tratado de borrar una consulta inexistente. Devolviendo 404 advisor.");
			return "redirect:/e/404";
		}

		if (!hasRole("ROLE_ADMIN")) {
			logger.warn(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
					+ "ha tratado de eliminar la consulta" + idSolicitud + " sin tener los privilegios necesarios.");
			return "redirect:/e/403";
		}

		if (solicitud.getFileBlobKey() != null) {
			/*
			 * Si no encuentra la blobkey no hay nullPointer, aparentemente,
			 * simplemente lo omite.
			 */
			template.delete(new Query(Criteria.where("_id").is(solicitud.getFileBlobKey())));
		}

		repo.delete(solicitud);
		logger.info(principal.getName() + " " + req.getRemoteAddr() + " " + req.getLocalAddr()
				+ " ha borrado la consulta " + idSolicitud);
		return "redirect:/offersconsulting";
	}

	private boolean hasRole(String role) {
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}
	
	
	@RequestMapping(value = "/serve", params = { "id" }, method = RequestMethod.GET)
	public @ResponseBody
	void download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException {
		SolicitudFinancialAdvisorDomain solicitud = repo.findOne(id);
		
		if (solicitud == null) {
			logger.warn(principal.getName() + " " + request.getRemoteAddr() + " " + request.getLocalAddr()
					+ " ha tratado de acceder a una consulta inexistente. ");
		}else {
			GridFSDBFile file = template.findOne(new Query(Criteria.where("_id").is(solicitud.getFileBlobKey())));
			
			if (file == null) {
		    	logger.warn(principal.getName() + " " + request.getRemoteAddr() + " " + request.getLocalAddr()
				+ " ha tratado de acceder a un archivo inexistente de la consulta " + id);
			}else if(hasRole("ROLE_ADVISORUSER") && !principal.getName().equals(solicitud.getEmail())) {
		    	logger.warn(principal.getName() + " " + request.getRemoteAddr() + " " + request.getLocalAddr()
				+ " ha tratado de acceder a un archivo de la consulta " + id+ " sin tener permisos");
		    }else {
		    	try {
		    		response.setContentType(file.getContentType());
			    	response.setContentLength((new Long(file.getLength()).intValue()));
			    	response.setHeader("content-Disposition", "attachment; filename=" + file.getFilename());// "attachment;filename=test.xls"
			        IOUtils.copyLarge(file.getInputStream(), response.getOutputStream());
			    } catch (IOException ex) {
			    	logger.info("Error writing file to output stream. Filename was '" + id + "'");
			    	throw new RuntimeException("IOError writing file to output stream");
			    }
		    }
		}		
		
	}

}
