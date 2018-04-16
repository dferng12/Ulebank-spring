package es.unileon.ulebankoffice.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.unileon.ulebankoffice.service.InteresVariable;


public class TAEVariosInteresesDomainTest {
	
	
	private TAEVariosInteresesDomain tae;
			
	@Test
	public void testCalcularPeriodo12() {
	
		List<InteresVariable> intereses = new ArrayList<>();
		intereses.add(new InteresVariable(1));
		intereses.add(new InteresVariable(2));
		intereses.add(new InteresVariable(3));
		intereses.add(new InteresVariable(4));
		intereses.add(new InteresVariable(5));
		intereses.add(new InteresVariable(6));
		intereses.add(new InteresVariable(7));
		intereses.add(new InteresVariable(8));
		intereses.add(new InteresVariable(9));
		intereses.add(new InteresVariable(10));
		intereses.add(new InteresVariable(11));
		intereses.add(new InteresVariable(12));
		tae = new TAEVariosInteresesDomain(1, intereses);
		assertThat(tae.calcular(), is("1.00"));
		tae.setPeriodo(2);
		assertThat(tae.calcular(), is("1.50"));
		tae.setPeriodo(3);
		assertThat(tae.calcular(), is("2.01"));
		tae.setPeriodo(4);
		assertThat(tae.calcular(), is("2.52"));
		tae.setPeriodo(6);
		assertThat(tae.calcular(), is("3.53"));
		tae.setPeriodo(12);
		assertThat(tae.calcular(), is("6.63"));
	}

}
