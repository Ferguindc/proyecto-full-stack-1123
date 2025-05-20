package grupo2.proyecto.full.stack1.Repository;

import grupo2.proyecto.full.stack1.Modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clienteRepository extends JpaRepository<Cliente, Integer> {
}
