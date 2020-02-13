package integracao.teste.frete;

import integracao.teste.frete.Cidade;
import integracao.teste.frete.CidadeRepository;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CidadesRepositoryIntegrationTest {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Cidade cidade;

	@Before
	public void start() {
		cidade = new Cidade("São Luís", "MA", 0.5);
	}

	@Test
	public void saveComNomeNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O nome deve ser preenchido");

		cidade.setNome(null);
		cidadeRepository.save(cidade);
	}

	@Test
	public void saveComUFNulaDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("A UF deve ser preenchida");

		cidade.setUf(null);
		cidadeRepository.save(cidade);
	}

	@Test
	public void saveComTaxaNulaDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("A taxa deve ser preenchida");

		cidade.setTaxa(null);
		cidadeRepository.save(cidade);
	}
	
	@Test
	public void saveComTaxaIgualAZeroDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("A taxa deve ser maior ou igual a 0.1");

		cidade.setTaxa(0.0);
		cidadeRepository.save(cidade);
	}

	@Test
	public void saveDeveSalvarCidade() {
		cidadeRepository.save(cidade);
		List<Cidade> cidades = cidadeRepository.findAll();
		Assert.assertEquals(1, cidades.size());
		cidadeRepository.deleteAll();
	}

	@Test
	public void deleteByCodigoDeveRemoverCidade() {
		cidadeRepository.save(cidade);
		List<Cidade> cidades = cidadeRepository.findAll();
		Assert.assertEquals(1, cidades.size());

		cidadeRepository.deleteById(cidade.getCodigo());
		List<Cidade> resultado = cidadeRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}