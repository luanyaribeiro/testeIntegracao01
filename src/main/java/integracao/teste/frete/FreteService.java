package integracao.teste.frete;

import integracao.teste.exception.FreteException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FreteService {

	@Autowired
	private FreteRepository freteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void inserir(Frete frete) throws FreteException {
		if(frete.getCidade() == null)
			throw new FreteException("A cidade deve ser preenchida");
		
		if(frete.getCliente() == null)
			throw new FreteException("O cliente deve ser preenchido");
		
		List<Cidade> cidades = cidadeRepository.buscaPor(frete.getCidade().getNome());
		if(cidades.isEmpty())
			throw new FreteException("A cidade não foi encontrada.");
		
		List<Cliente> clientes = clienteRepository.buscaPor(frete.getCliente().getTelefone());
		if(clientes.isEmpty())
			throw new FreteException("O cliente não foi encontrado.");
		
		try {
			freteRepository.save(frete);
		} catch (Exception e) {
			throw new FreteException(e);
		}
	}

	public Double calcularValorFrete(Double peso, Double valorFixo, Cidade cidade) throws FreteException {		
		if(peso == null || peso <= 0.0)
			throw new FreteException("O peso deve ser maior que zero.");
		
		if(valorFixo == null || valorFixo <= 0.0)
			throw new FreteException("O valor fixo deve ser maior que zero.");
		
		if(cidade == null)
			throw new FreteException("Informar a cidade é obrigatório.");
		
		List<Cidade> cidades = cidadeRepository.buscaPor(cidade.getNome());
		if(cidades.isEmpty())
			throw new FreteException("A cidade não foi encontrada.");
		
		return (peso * valorFixo) + cidades.get(0).getTaxa();
	}

	public Frete recuperarFreteDeMaiorValor() throws FreteException {
		List<Frete> fretes = freteRepository.findAll(new Sort(Sort.Direction.DESC, "valor"));
		
		if(fretes.isEmpty())
			throw new FreteException("Fretes não encontrados.");
		
		return fretes.get(0);
	}
	
	public Cidade recuperarCidadeComMaisFretes() throws FreteException {		
		Cidade cidadeMaisFrete = null;
		int qtdCidadeMaisFrete = 0;
		
		List<Cidade> cidades = cidadeRepository.findAll();
		for (Cidade cidade : cidades) {
			List<Frete> fretes = freteRepository.todosPorCidade(cidade.getCodigo());
			
			if(fretes.size() > qtdCidadeMaisFrete) {
				qtdCidadeMaisFrete = fretes.size(); 
				cidadeMaisFrete = cidade;
			}
		}
		
		return cidadeMaisFrete;
	}
	
	public void remover(Long id) throws FreteException {
		try {
			freteRepository.deleteById(id);
		} catch (Exception e) {
			throw new FreteException(e);
		}
	}

}