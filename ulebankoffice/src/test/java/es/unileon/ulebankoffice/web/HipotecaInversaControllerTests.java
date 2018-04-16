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

public class HipotecaInversaControllerTests {

	@InjectMocks
    private HipotecaInversaFormController hipotecaInversaController;
 
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setSuffix(".jsp");
        this.mockMvc = MockMvcBuilders.standaloneSetup(hipotecaInversaController)
        		.setViewResolvers(vr)
        		.build();
    }

	@Test
	public void testGetRequest() throws Exception {
		this.mockMvc.perform(get("/reversemortgage"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"));
	}
	
	@Test
	public void testPostRequestWithoutErrors() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().attributeExists("tabla"));
	}
	
	@Test
	public void testPostRequestValorTasacionError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "0")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestEdadError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "64")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestPorcentajeSobreTasacionError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "26000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "-1")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestTipoInteresPrestamoError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "-1")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestComisionAperturaError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "-1")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestRentabilidadRentaError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "-1")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestCosteTasacionError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "-1")
	            .param("gastosFormalizacion", "2896"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}
	
	@Test
	public void testPostRequestGastosFormalizacionError() throws Exception {
		this.mockMvc.perform(post("/reversemortgage")
				.param("valorTasacion", "260000")
	            .param("edad", "75")
	            .param("porcentajeSobreTasacion", "50")
	            .param("tipoInteresPrestamo", "4.70")
	            .param("comisionApertura", "1.5")
	            .param("rentabilidadRenta", "2.85")
	            .param("costeTasacion", "300")
	            .param("gastosFormalizacion", "-1"))
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("reversemortgage.jsp"))
	            .andExpect(model().hasErrors());
	}

}
