package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.detallePedido;
import grupo2.proyecto.full.stack1.Repository.detallePedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class DetallePedidoService {
    @Autowired
    private detallePedidoRepository detallePedidoRepository;

    public List<detallePedido> findAll() {
        return detallePedidoRepository.findAll();
    }

    public detallePedido findById(int id) {
        return detallePedidoRepository.findById(id).get();
    }
    public detallePedido save(detallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);

    }
    public void delete(int id) {
        detallePedidoRepository.deleteById(id);
    }

}
