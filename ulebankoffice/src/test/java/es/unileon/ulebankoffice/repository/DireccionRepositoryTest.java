package es.unileon.ulebankoffice.repository;
/**
 * @author Razvan Raducu
 *
 */

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
import es.unileon.ulebankoffice.domain.DireccionDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfig.class)
public class DireccionRepositoryTest {
	
	@Autowired
	Mongo mongo;

	@Autowired
	private ApplicationContext applicationContext;

	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("ulebankofficetestdb");

	@Autowired
	private DireccionRepository repo;
	

	@After
	public void afterEachMethod() throws Exception {
		repo.deleteAll();
	}
	
	@Test
	public void isUsingFongo() {
		assertEquals("Fongo (ulebankofficetestdb)", mongo.toString());
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/direccionRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testFindByDni() {
		List<DireccionDomain> direcciones = repo.findByDni("X5526828C");
		assertThat(direcciones.size(), is(3));
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/direccionRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testFindByDniNonExistent() {
	List<DireccionDomain> direcciones = repo.findByDni("X5526828D");
	assertThat(direcciones.size(), is(0));
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/direccionRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAll() {
	List<DireccionDomain> direcciones = repo.findAll();
	assertThat(direcciones.size(), is(3));
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/direccionRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testDelete() {
		List<DireccionDomain> direcciones = repo.findByDni("X5526828C");
		assertThat(direcciones.size(), is(3));
		repo.delete(direcciones.get(0));
		assertThat(repo.findByDni("X5526828C").size(), is(2));
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/direccionRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteById() {
		List<DireccionDomain> direcciones = repo.findByDni("X5526828C");
		assertThat(direcciones.size(), is(3));
		repo.deleteByDni("X5526828C");
		assertThat(repo.findByDni("X5526828C").size(), is(0));
	}
	
	@Test
	@UsingDataSet(locations = { "/testing/direccionRepositoryData.json" }, 
	loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void testSave() {
		List<DireccionDomain> direcciones = repo.findByDni("X5526828C");
		assertThat(direcciones.size(), is(3));
		assertThat(direcciones.get(0).getComunidadAutonoma(), is("CastillaLeón"));
		direcciones.get(0).setComunidadAutonoma("Castilla y León");
		repo.save(direcciones.get(0));
		direcciones = repo.findByDni("X5526828C");
		for (DireccionDomain direccionDomain : direcciones) {
			assertThat(direccionDomain.getComunidadAutonoma(), is(not("León")));
		}
				
	}
	
	
	
}
