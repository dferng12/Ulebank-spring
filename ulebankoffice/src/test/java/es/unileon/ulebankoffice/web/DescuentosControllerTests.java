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

public class DescuentosControllerTests {
	
	@InjectMocks
    private DescuentosFormController descuentosController;
 
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        this.mockMvc = MockMvcBuilders.standaloneSetup(descuentosController)
        		.setViewResolvers(viewResolver)
        		.build();
    }

    @Test
	public void testGetRequest() throws Exception {
		this.mockMvc.perform(get("/discounts"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"));
	}

    @Test
	public void testPostRequestWithoutErrors() throws Exception {
		this.mockMvc.perform(post("/discounts")
				.param("importeDescuento", "1000")
	            .param("periodoDescuento", "2")
	            .param("tipoInteres", "2.55")
	            .param("base", "365")
	            .param("otrosGastos", "160")
	            .param("comisiones", "3"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"))
	            .andExpect(model().attributeExists("tabla"));
	}
    
    @Test
	public void testPostRequestImporteDescuentoError() throws Exception {
		this.mockMvc.perform(post("/discounts")
				.param("importeDescuento", "0")
	            .param("periodoDescuento", "2")
	            .param("tipoInteres", "2.55")
	            .param("base", "365")
	            .param("otrosGastos", "160")
	            .param("comisiones", "3"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"))
	            .andExpect(model().hasErrors());
	}
    
    @Test
	public void testPostRequestPeriodoDescuentoError() throws Exception {
		this.mockMvc.perform(post("/discounts")
				.param("importeDescuento", "1000")
	            .param("periodoDescuento", "0")
	            .param("tipoInteres", "2.55")
	            .param("base", "365")
	            .param("otrosGastos", "160")
	            .param("comisiones", "3"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"))
	            .andExpect(model().hasErrors());
	}
    
    @Test
	public void testPostRequestTipoInteresError() throws Exception {
		this.mockMvc.perform(post("/discounts")
				.param("importeDescuento", "1000")
	            .param("periodoDescuento", "2")
	            .param("tipoInteres", "-2.56")
	            .param("base", "365")
	            .param("otrosGastos", "160")
	            .param("comisiones", "3"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"))
	            .andExpect(model().hasErrors());
	}
    
    @Test
	public void testPostRequestOtrosGastosError() throws Exception {
		this.mockMvc.perform(post("/discounts")
				.param("importeDescuento", "1000")
	            .param("periodoDescuento", "2")
	            .param("tipoInteres", "2.55")
	            .param("base", "365")
	            .param("otrosGastos", "-100")
	            .param("comisiones", "3"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"))
	            .andExpect(model().hasErrors());
	}
    
    @Test
	public void testPostRequestComisionesError() throws Exception {
		this.mockMvc.perform(post("/discounts")
				.param("importeDescuento", "1000")
	            .param("periodoDescuento", "2")
	            .param("tipoInteres", "2.55")
	            .param("base", "365")
	            .param("otrosGastos", "160")
	            .param("comisiones", "-3"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("discounts.jsp"))
	            .andExpect(model().hasErrors());
	}
}
