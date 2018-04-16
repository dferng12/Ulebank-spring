/**
 * 
 */
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

/**
 * @author Razvan Raducu
 *
 */
public class TAEVariosInteresesControllerTest {

	@InjectMocks
	private TAEVariosInteresesController controller;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setSuffix(".jsp");
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testGetRequest() throws Exception {
		this.mockMvc.perform(get("/aprv"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("aprv.jsp"));
	}

	@Test
	public void testPost() throws Exception {
		this.mockMvc.perform(post("/aprv")
				.param("periodo","2")
				.param("intereses[0].interes", "2")
				.param("intereses[1].interes", "3"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("aprv.jsp"))
				.andExpect(model().attributeExists("datosTaeVariosIntereses"));
				
	}
	
	@Test
	public void testPostErrores() throws Exception {
		this.mockMvc.perform(post("/aprv")
				.param("periodo","a")
				.param("intereses[0].interes", "2")
				.param("intereses[1].interes", "3"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("aprv.jsp"))
				.andExpect(model().hasErrors());
				
	}

}
