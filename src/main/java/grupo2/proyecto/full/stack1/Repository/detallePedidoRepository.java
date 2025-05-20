package grupo2.proyecto.full.stack1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import grupo2.proyecto.full.stack1.Modelo.detallePedido;

@Repository
public interface detallePedidoRepository extends JpaRepository<detallePedido, Integer> {
}
