package integracao.teste.frete;

import integracao.teste.frete.Cidade;
import integracao.teste.frete.Cliente;
import integracao.teste.frete.Frete;
import integracao.teste.frete.FreteRepository;

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
public class FretesRepositoryIntegrationTest {

	@Autowired
	private FreteRepository freteRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Frete frete;

	@Before
	public void start() {
		Cidade cidade = new Cidade("São Luís", "MA", 0.5);
		Cliente cliente = new Cliente("Luany Ribeiro", "R. Principal", "98988888888");
		
		frete = new Frete(cidade, cliente, "Frete para São Luís", 0.2, 0.5);
	}

	@Test
	public void saveComCidadeNulaDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("A cidade deve ser preenchida");

		frete.setCidade(null);
		freteRepository.save(frete);
	}

	@Test
	public void saveComClienteNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O cliente deve ser preenchido");

		frete.setCliente(null);
		freteRepository.save(frete);
	}

	@Test
	public void saveComDescricaoNulaDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("A descrição deve ser preenchida");

		frete.setDescricao(null);
		freteRepository.save(frete);
	}
	
	@Test
	public void saveComPesoNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O peso deve ser preenchido");

		frete.setPeso(null);
		freteRepository.save(frete);
	}
	
	@Test
	public void saveComPesoIgualAZeroDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O peso deve ser maior ou igual a 0.1");

		frete.setPeso(0.0);
		freteRepository.save(frete);
	}
	
	@Test
	public void saveComValorNulaDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O valor deve ser preenchido");

		frete.setValor(null);
		freteRepository.save(frete);
	}
	
	@Test
	public void saveComValorIgualAZeroDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O valor deve ser maior ou igual a 0.1");

		frete.setValor(0.0);
		freteRepository.save(frete);
	}

	@Test
	public void saveDeveSalvarFrete() {
		freteRepository.save(frete);
		List<Frete> fretes = freteRepository.findAll();
		Assert.assertEquals(1, fretes.size());
		freteRepository.deleteAll();
	}

	@Test
	public void deleteByCodigoDeveRemoverFrete() {
		freteRepository.save(frete);
		List<Frete> fretes = freteRepository.findAll();
		Assert.assertEquals(1, fretes.size());

		freteRepository.deleteById(frete.getCodigo());
		List<Frete> resultado = freteRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}