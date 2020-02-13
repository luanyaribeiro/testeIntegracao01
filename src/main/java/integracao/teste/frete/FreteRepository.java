package integracao.teste.frete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreteRepository extends JpaRepository<Frete, Long> {

    @Query(value = "select f from Frete f where f.cliente.codigo = ?1 order by f.valor")
    List<Frete> todosPorCliente(Long codigoCliente);
    
    @Query(value = "select f from Frete f where f.cidade.codigo = ?1")
    List<Frete> todosPorCidade(Long codigoCidade);
}
