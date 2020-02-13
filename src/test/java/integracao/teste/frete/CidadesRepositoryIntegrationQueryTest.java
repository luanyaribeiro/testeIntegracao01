package integracao.teste.frete;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.teste.frete.Cidade;
import integracao.teste.frete.CidadeRepository;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CidadesRepositoryIntegrationQueryTest {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Before
	public void before() {
		Cidade cidade = new Cidade("São Luís", "MA", 0.1);
		cidadeRepository.save(cidade);
		cidade = new Cidade("Imperatriz", "MA", 2.5);
		cidadeRepository.save(cidade);
		cidade = new Cidade("Fortaleza", "CE", 5.0);
		cidadeRepository.save(cidade);
		cidade = new Cidade("Belém", "PA", 6.2);
		cidadeRepository.save(cidade);
	}

	@After
	public void after() {
		cidadeRepository.deleteAll();
	}

	@Test
	public void deveBuscarCidadePeloNome() {
		List<Cidade> cidades = cidadeRepository.buscaPor("Fortaleza");
		Assert.assertTrue(cidades.get(0).getNome().equals("Fortaleza"));
	}

	@Test
	public void deveRetornarTodasAsCidadesOrdenadasPorNomeEmOrdemCrescente()  {
		List<Cidade> cidades = cidadeRepository.findAll(new Sort(Sort.Direction.ASC, "nome"));
		
		Assert.assertTrue(cidades.get(0).getNome().equals("Belém"));
		Assert.assertTrue(cidades.get(1).getNome().equals("Fortaleza"));
		Assert.assertTrue(cidades.get(2).getNome().equals("Imperatriz"));
		Assert.assertTrue(cidades.get(3).getNome().equals("São Luís"));
	}
	
	@Test
	public void deveRetornarUmaCidadePeloCodigo()  {
		List<Cidade> cidades = cidadeRepository.findAll();
		Optional<Cidade> cidade = cidadeRepository.findById(cidades.get(0).getCodigo());
		
		Assert.assertTrue(cidade.get().getNome().equals("São Luís"));
	}

}