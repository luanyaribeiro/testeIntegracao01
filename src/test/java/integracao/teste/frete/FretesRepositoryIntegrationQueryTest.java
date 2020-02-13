package integracao.teste.frete;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.teste.frete.Cidade;
import integracao.teste.frete.Cliente;
import integracao.teste.frete.Frete;
import integracao.teste.frete.FreteRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FretesRepositoryIntegrationQueryTest {

	@Autowired
	private FreteRepository freteRepository;

	@Before
	public void before() {
		Cidade cidade = new Cidade("São Luís", "MA", 0.1);
		Cliente cliente = new Cliente("Luany Ribeiro", "R. Principal", "98988888888");
		Frete frete = new Frete(cidade, cliente, "Frete para São Luís", 100.0, 10.0);
		freteRepository.save(frete);
		
		cidade = new Cidade("Imperatriz", "MA", 2.5);
		cliente = new Cliente("Pedro da Silva", "Rua Oswaldo Rodrigues", "98922222222");
		frete = new Frete(cidade, cliente, "Frete para Imperatriz", 50.5, 126.25);
		freteRepository.save(frete);
		
		cidade = new Cidade("Fortaleza", "CE", 5.0);
		cliente = new Cliente("Davi de Oliveira", "Avenida Central", "98981818181");
		frete = new Frete(cidade, cliente, "Frete para Fortaleza", 7.9, 39.5);
		freteRepository.save(frete);
		
		cidade = new Cidade("Belém", "PA", 6.2);
		cliente = new Cliente("Maria Pereira", "Av. Luciano das Neves", "98985868789");
		frete = new Frete(cidade, cliente, "Frete para Belém", 151.4, 938.68);
		freteRepository.save(frete);
	}

	@After
	public void after() {
		freteRepository.deleteAll();
	}
	
	@Test
	public void deveRetornarTodosOsFretesOrdenadosPorDescricaoEmOrdemCrescente()  {
		List<Frete> fretes = freteRepository.findAll(new Sort(Sort.Direction.ASC, "descricao"));
		
		Assert.assertTrue(fretes.get(0).getDescricao().equals("Frete para Belém"));
		Assert.assertTrue(fretes.get(1).getDescricao().equals("Frete para Fortaleza"));
		Assert.assertTrue(fretes.get(2).getDescricao().equals("Frete para Imperatriz"));
		Assert.assertTrue(fretes.get(3).getDescricao().equals("Frete para São Luís"));
	}

	@Test
	public void deveRetornarTodosOsFretesDeUmClienteOrdenadosPorValorEmOrdemCrescente()  {
		List<Frete> todosOsFretes = freteRepository.findAll();
		List<Frete> fretes = freteRepository.todosPorCliente(todosOsFretes.get(0).getCliente().getCodigo());
		
		Assert.assertTrue(fretes.get(0).getCliente().getNome().equals("Luany Ribeiro"));
	}
	
	@Test
	public void deveRetornarTodosOsFretesDeUmaCidade()  {
		List<Frete> todosOsFretes = freteRepository.findAll();
		List<Frete> fretes = freteRepository.todosPorCliente(todosOsFretes.get(0).getCidade().getCodigo());
		
		Assert.assertTrue(fretes.get(0).getCidade().getNome().equals("São Luís"));
	}

}