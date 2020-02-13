package integracao.teste.frete;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FreteControllerIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

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
	
	private String descricao = "Frete para São Luís";
	private Double peso = 0.2;
	private Double valor = 0.5;

	@Before
	public void start() {
		cidade = new Cidade("São Luís", "MA", 0.5);
		cidadeRepository.save(cidade);
		
		cliente = new Cliente("Luany Ribeiro", "R. Principal", "98988888888");
		clienteRepository.save(cliente);
		
		frete = new Frete(cidade, cliente, descricao, peso, valor);		
		freteRepository.save(frete);
	}

	@After
	public void end() {
		freteRepository.deleteAll();
	}
	
	@Test
	public void deveBuscarOFreteDeMaiorValor() {
		ResponseEntity<Frete> resposta =
				testRestTemplate.exchange("/frete/freteDeMaiorValor/", HttpMethod.GET, null, Frete.class);

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(
				MediaType.parseMediaType("application/json;charset=UTF-8")));
		Assert.assertEquals(new Double(0.5), resposta.getBody().getValor());
	}
	
	@Test
	public void deveBuscarACidadeComMaisFretes() {
		ResponseEntity<Cidade> resposta =
				testRestTemplate.exchange("/frete/cidadeComMaisFretes/", HttpMethod.GET, null, Cidade.class);

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(
				MediaType.parseMediaType("application/json;charset=UTF-8")));
		Assert.assertEquals("São Luís", resposta.getBody().getNome());
	}

	@Test
	public void inserirDeveSalvarContato() {
		Frete frete = new Frete(cidade, cliente, descricao, peso, valor);
		HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

		ResponseEntity<Frete> resposta =
				testRestTemplate.exchange("/frete/inserir",HttpMethod.POST, httpEntity, Frete.class);

		Assert.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

		Frete resultado = resposta.getBody();

		Assert.assertNotNull(resultado.getCodigo());
		freteRepository.deleteAll();
	}

	@Test
	public void removerDeveExcluirContato() {
		ResponseEntity<Frete> resposta = 
				testRestTemplate.exchange("/frete/remover/{id}", HttpMethod.DELETE, null, Frete.class, frete.getCodigo());
		
		Assert.assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}

}