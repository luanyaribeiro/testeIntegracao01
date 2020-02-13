package integracao.teste.frete;

import integracao.teste.frete.Cliente;
import integracao.teste.frete.ClienteRepository;

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
public class ClientesRepositoryIntegrationTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Cliente cliente;

	@Before
	public void start() {
		cliente = new Cliente("Luany Ribeiro", "R. Principal", "98988888888");
	}

	@Test
	public void saveComNomeNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O nome deve ser preenchido");

		cliente.setNome(null);
		clienteRepository.save(cliente);
	}

	@Test
	public void saveComEnderecoNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O endereço deve ser preenchido");

		cliente.setEndereco(null);
		clienteRepository.save(cliente);
	}

	@Test
	public void saveComTelefoneNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O telefone deve ser preenchido");

		cliente.setTelefone(null);
		clienteRepository.save(cliente);
	}

	@Test
	public void saveDeveSalvarCliente() {
		clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findAll();
		Assert.assertEquals(1, clientes.size());
		clienteRepository.deleteAll();
	}

	@Test
	public void deleteByCodigoDeveRemoverCliente() {
		clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findAll();
		Assert.assertEquals(1, clientes.size());

		clienteRepository.deleteById(cliente.getCodigo());
		List<Cliente> resultado = clienteRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}