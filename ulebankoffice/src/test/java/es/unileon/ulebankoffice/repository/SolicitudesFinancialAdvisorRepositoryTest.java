package es.unileon.ulebankoffice.repository;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;

import es.unileon.ulebankoffice.configuration.MongoTestConfig;
import es.unileon.ulebankoffice.domain.SolicitudFinancialAdvisorDomain;

/**
 * @author Razvan Raducu
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfig.class)
public class SolicitudesFinancialAdvisorRepositoryTest {
	
	@Autowired
	Mongo mongo;

	@Autowired
	private ApplicationContext applicationContext;

	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("ulebankofficetestdb");

	@Autowired
	private SolicitudesFinancialAdvisorRepository repo;
	
	@After
	public void afterEach(){
		repo.deleteAll();
	}
	
	@Test
	public void isUsingFongo() {
		assertEquals("Fongo (ulebankofficetestdb)", mongo.toString());
	}

	@Test
	@UsingDataSet(locations = { "/testing/solicitudesFinancialAdvisorRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testFindByEmailOrderByEstadoDesc() {
		List<SolicitudFinancialAdvisorDomain> lista = repo.findByEmailOrderByEstadoDesc("rraduc00@estudiantes.unileon.es");
		assertThat(lista.get(0).getEstado(), is("Pendiente"));
		assertThat(lista.get(1).getEstado(), is("Contestada"));
		assertThat(lista.get(0).getTextoOferta(), is("texto"));
		assertThat(lista.get(1).getTextoOferta(), is("texto123"));
		assertThat(lista.size(), is(2));
	}

	@Test
	@UsingDataSet(locations = { "/testing/solicitudesFinancialAdvisorRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAllByOrderByEstadoDesc() {
		List<SolicitudFinancialAdvisorDomain> lista = repo.findAllByOrderByEstadoDesc();
		assertThat(lista.size(), is(4));
		assertThat(lista.get(0).getEstado(), is("Pendiente"));
		assertThat(lista.get(1).getEstado(), is("Pendiente"));
		assertThat(lista.get(2).getEstado(), is("Contestada"));
		assertThat(lista.get(3).getEstado(), is("Contestada"));
		
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/solicitudesFinancialAdvisorRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testFindByEmailNonExistent() {
		List<SolicitudFinancialAdvisorDomain> lista = repo.findByEmailOrderByEstadoDesc("no@estudiantes.unileon.es");
		assertThat(lista.size(), is(0));
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/solicitudesFinancialAdvisorRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testSave() {
		List<SolicitudFinancialAdvisorDomain> lista = repo.findAll();
		assertThat(lista.size(), is(4));
		repo.save(new SolicitudFinancialAdvisorDomain());
		lista = repo.findAll();
		assertThat(lista.size(), is(5));
		
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/solicitudesFinancialAdvisorRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testDelete() {
		List<SolicitudFinancialAdvisorDomain> lista = repo.findAll();
		assertThat(lista.size(), is(4));
		repo.delete(lista.get(0));
		lista = repo.findAll();
		assertThat(lista.size(), is(3));
		repo.delete(lista.get(0));
		assertThat(repo.findAll().size(), is(2));
		repo.delete(lista);
		lista = repo.findAll();
		assertThat(lista.size(), is(0));
		
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/solicitudesFinancialAdvisorRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteAll() {
		List<SolicitudFinancialAdvisorDomain> lista = repo.findAll();
		assertThat(lista.size(), is(4));
		repo.deleteAll();
		lista = repo.findAll();
		assertThat(lista.size(), is(0));
		
	}

}
