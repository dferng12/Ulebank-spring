package es.unileon.ulebankoffice.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TAEVariosInteresesTest {
	
	private TAEVariosIntereses tae;
	
	@Before
	public void setUp() throws Exception {
		tae = new TAEVariosIntereses();
	}

	@Test
	public void testIntereses() {
		assertNull(tae.getIntereses());
		tae.setIntereses(new ArrayList<InteresVariable>());
		assertThat(tae.getIntereses().size(), is(0));
	}

	@Test
	public void testPeriodo() {
		assertThat(tae.getPeriodo(), is(0));
		tae.setPeriodo(8);
		assertThat(tae.getPeriodo(), is(8));
	}


}
