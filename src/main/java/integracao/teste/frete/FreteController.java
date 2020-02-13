package integracao.teste.frete;

import integracao.teste.exception.FreteException;
import integracao.teste.frete.Cidade;
import integracao.teste.frete.Frete;
import integracao.teste.frete.FreteService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frete")
public class FreteController {

	@Autowired
	private FreteService freteService;

	@GetMapping("/freteDeMaiorValor")
	public ResponseEntity<Frete> freteDeMaiorValor() throws FreteException {
		Frete frete = freteService.recuperarFreteDeMaiorValor();
		return ResponseEntity.ok(frete);
	}
	
	@GetMapping("/cidadeComMaisFretes")
	public ResponseEntity<Cidade> cidadeComMaisFretes() throws FreteException {
		Cidade cidade = freteService.recuperarCidadeComMaisFretes();
		return ResponseEntity.ok(cidade);
	}

	@PostMapping("/inserir")
	public ResponseEntity<Frete> inserir(@RequestBody @Valid Frete contato) throws FreteException {
		freteService.inserir(contato);
		return new ResponseEntity<>(contato, HttpStatus.CREATED);
	}

	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Frete> remover(@PathVariable Long id) throws FreteException {
		freteService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/valorFrete")
	public ResponseEntity<Double> valorFrete(@RequestBody @Valid Double peso, @RequestBody @Valid Double valorFixo, @RequestBody @Valid Cidade cidade) throws FreteException{
		Double valorFrete = freteService.calcularValorFrete(peso, valorFixo, cidade);
		return ResponseEntity.ok(valorFrete);
	}

}