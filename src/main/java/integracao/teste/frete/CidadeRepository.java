package integracao.teste.frete;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	@Query(value = "from Cidade where nome = ?1")
	List<Cidade> buscaPor(String nome);
}
