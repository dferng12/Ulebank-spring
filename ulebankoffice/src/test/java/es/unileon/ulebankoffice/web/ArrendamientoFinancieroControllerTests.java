package es.unileon.ulebankoffice.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ArrendamientoFinancieroControllerTests {
	
	@InjectMocks
    private ArrendamientoFinancieroFormController arrendamientoFinancieroController;
 
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        //Necesario declarar el viewresolver para evitar ciruclar path
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        this.mockMvc = MockMvcBuilders.standaloneSetup(arrendamientoFinancieroController)
        		.setViewResolvers(viewResolver)
        		.build();
    }

    @Test
	public void testGetRequest() throws Exception {
		this.mockMvc.perform(get("/leasing"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("leasing.jsp"));
	}
	
	@Test
	public void testPostRequestWithoutErrors() throws Exception {
		this.mockMvc.perform(post("/leasing")
				.param("valorBien", "12000")
	            .param("duracionContrato", "5")
	            .param("fraccionamientoPagoCuota", "3")
	            .param("tipoInteres", "2.50"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("leasing.jsp"))
	            .andExpect(model().attributeExists("tabla"));
	}
	
	@Test
	public void testPostRequestValorBienError() throws Exception {
		this.mockMvc.perform(post("/leasing")
				.param("valorBien", "0")
	            .param("duracionContrato", "5")
	            .param("fraccionamientoPagoCuota", "3")
	            .param("tipoInteres", "2.50"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("leasing.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestDuracionContratoError() throws Exception {
		this.mockMvc.perform(post("/leasing")
				.param("valorBien", "12000")
	            .param("duracionContrato", "0")
	            .param("fraccionamientoPagoCuota", "3")
	            .param("tipoInteres", "2.50"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("leasing.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestTipoInteresError() throws Exception {
		this.mockMvc.perform(post("/leasing")
				.param("valorBien", "12000")
	            .param("duracionContrato", "5")
	            .param("fraccionamientoPagoCuota", "3")
	            .param("tipoInteres", "-1"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("leasing.jsp"))
	            .andExpect(model().hasErrors());
	}
}
