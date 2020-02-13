package integracao.teste.frete;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.teste.frete.Cliente;
import integracao.teste.frete.ClienteRepository;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientesRepositoryIntegrationQueryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Before
	public void before() {
		Cliente cliente = new Cliente("Luany Ribeiro", "R. Principal", "98988888888");
		clienteRepository.save(cliente);
		cliente = new Cliente("Pedro da Silva", "Rua Oswaldo Rodrigues", "98922222222");
		clienteRepository.save(cliente);
		cliente = new Cliente("Davi de Oliveira", "Avenida Central", "98981818181");
		clienteRepository.save(cliente);
		cliente = new Cliente("Maria Pereira", "Av. Luciano das Neves", "98985868789");
		clienteRepository.save(cliente);
	}

	@After
	public void after() {
		clienteRepository.deleteAll();
	}

	@Test
	public void deveBuscarClientePeloTelefone() {
		List<Cliente> clientes = clienteRepository.buscaPor("98988888888");
		Assert.assertTrue(clientes.get(0).getTelefone().equals("98988888888"));
	}

	@Test
	public void deveRetornarTodosOsClientesOrdenadosPorNomeEmOrdemCrescente()  {
		List<Cliente> clientes = clienteRepository.findAll(new Sort(Sort.Direction.ASC, "nome"));
		
		Assert.assertTrue(clientes.get(0).getNome().equals("Davi de Oliveira"));
		Assert.assertTrue(clientes.get(1).getNome().equals("Luany Ribeiro"));
		Assert.assertTrue(clientes.get(2).getNome().equals("Maria Pereira"));
		Assert.assertTrue(clientes.get(3).getNome().equals("Pedro da Silva"));
	}
	
	@Test
	public void deveRetornarUmClientePeloCodigo()  {
		List<Cliente> clientes = clienteRepository.findAll();
		Optional<Cliente> cliente = clienteRepository.findById(clientes.get(0).getCodigo());
		
		Assert.assertTrue(cliente.get().getNome().equals("Luany Ribeiro"));
	}

}