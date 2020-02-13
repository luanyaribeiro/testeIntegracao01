package integracao.teste.frete;

import integracao.teste.exception.FreteException;
import integracao.teste.frete.Cidade;
import integracao.teste.frete.CidadeRepository;
import integracao.teste.frete.Cliente;
import integracao.teste.frete.ClienteRepository;
import integracao.teste.frete.Frete;
import integracao.teste.frete.FreteRepository;
import integracao.teste.frete.FreteService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreteServiceIntegrationTest {

	@Autowired
	private FreteService freteService;

	@Autowired
	private FreteRepository freteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Frete frete;
	private Cidade cidade;
	private Cliente cliente;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();


	@Before
	public void start() {
		cidade = new Cidade("São Luís", "MA", 0.5);
		cidadeRepository.save(cidade);
		
		cliente = new Cliente("Luany Ribeiro", "R. Principal", "98988888888");
		clienteRepository.save(cliente);
		
		frete = new Frete(cidade, cliente, "Frete para São Luís", 0.2, 0.5);
	}
	
	@Test
	public void insereComCidadeNulaDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("A cidade deve ser preenchida");

		frete.setCidade(null);
		freteService.inserir(frete);
	}

	@Test
	public void insereComClienteNuloDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O cliente deve ser preenchido");

		frete.setCliente(null);
		freteService.inserir(frete);
	}

	@Test
	public void insereComDescricaoNulaDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("A descrição deve ser preenchida");

		frete.setDescricao(null);
		freteService.inserir(frete);
	}
	
	@Test
	public void insereComPesoNuloDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O peso deve ser preenchido");

		frete.setPeso(null);
		freteService.inserir(frete);
	}
	
	@Test
	public void insereComPesoIgualAZeroDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O peso deve ser maior ou igual a 0.1");

		frete.setPeso(0.0);
		freteService.inserir(frete);
	}
	
	@Test
	public void insereComValorNulaDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O valor deve ser preenchido");

		frete.setValor(null);
		freteService.inserir(frete);
	}
	
	@Test
	public void insereComValorIgualAZeroDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O valor deve ser maior ou igual a 0.1");

		frete.setValor(0.0);
		freteService.inserir(frete);
	}
	
	@Test
	public void insereComCidadeNaoExistenteDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("A cidade não foi encontrada.");

		Cidade cidadeInvalida = new Cidade("Cidade invalida", "AA", 0.1);
		frete.setCidade(cidadeInvalida);
		freteService.inserir(frete);
	}
	
	@Test
	public void insereComClienteNaoExistenteDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O cliente não foi encontrado.");

		Cliente clienteInvalido = new Cliente("Cliente invalido", "Endereço invalido", "00000000000");
		frete.setCliente(clienteInvalido);
		freteService.inserir(frete);
	}

	@Test
	public void deveSalvarFrete() throws FreteException {
		freteService.inserir(frete);
		List<Frete> fretes = freteRepository.findAll();
		Assert.assertEquals(1, fretes.size());
		freteRepository.deleteAll();
	}
	
	@Test
	public void calcularValorFreteSemInformarPesoDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O peso deve ser maior que zero.");
		
		freteService.calcularValorFrete(0.0, 10.00, cidade);
	}
	
	@Test
	public void calcularValorFreteSemInformarValorFixoDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("O valor fixo deve ser maior que zero.");
		
		freteService.calcularValorFrete(5.0, 0.0, cidade);
	}
	
	@Test
	public void calcularValorFreteSemInformarCidadeDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("Informar a cidade é obrigatório.");
		
		freteService.calcularValorFrete(5.0, 10.0, null);
	}
	
	@Test
	public void calcularValorFreteInformandoCidadeInexistenteDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("A cidade não foi encontrada.");
		
		Cidade novaCidade = new Cidade("Cidade invalida", "AA", 0.0);
		freteService.calcularValorFrete(5.0, 10.0, novaCidade);
	}
	
	@Test
	public void deveCalcularValorFrete() throws FreteException {
		Double valorFrete = freteService.calcularValorFrete(5.0, 10.0, cidade);
		Assert.assertEquals(new Double(50.5), valorFrete);
	}
	
	@Test
	public void recuperarFreteDeMaiorValorSemNenhumFreteSalvoDeveLancarException() throws Exception {
		expectedException.expect(FreteException.class);
		expectedException.expectMessage("Fretes não encontrados.");
		
		freteService.recuperarFreteDeMaiorValor();
	}
	
	@Test
	public void deveRecuperarFreteDeMaior() throws FreteException {
		freteService.inserir(frete);		
		
		Frete maiorFrete = new Frete(cidade, cliente, "Frete para São Luís", 0.2, 8.3);
		freteService.inserir(maiorFrete);
		
		Frete frete = freteService.recuperarFreteDeMaiorValor();
		Assert.assertEquals(new Double(8.3), new Double(frete.getValor()));
		freteRepository.deleteAll();
	}
	
	@Test
	public void deveRecuperarCidadeComMaisFretes() throws FreteException {
		freteService.inserir(frete);		
		
		Frete freteMesmaCidade = new Frete(cidade, cliente, "Frete para São Luís", 0.2, 8.3);
		freteService.inserir(freteMesmaCidade);
		
		Cidade novaCidade = new Cidade("Rio de Janeiro", "RJ", 17.9);
		cidadeRepository.save(novaCidade);
		
		Frete freteOutraCidade = new Frete(novaCidade, cliente, "Frete para Rio de Janeiro", 0.9, 13.3);
		freteService.inserir(freteOutraCidade);
		
		Cidade cidade = freteService.recuperarCidadeComMaisFretes();
		Assert.assertEquals("São Luís", cidade.getNome());
		freteRepository.deleteAll();
	}

}
