package integracao.teste.frete;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    @Query(value = "from Cliente where telefone = ?1")
    List<Cliente> buscaPor(String telefone);
}
