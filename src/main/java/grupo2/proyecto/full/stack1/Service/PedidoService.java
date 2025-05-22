package grupo2.proyecto.full.stack1.Service;


import grupo2.proyecto.full.stack1.Modelo.pedido;
import grupo2.proyecto.full.stack1.Repository.pedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class PedidoService {
    @Autowired
    private pedidoRepository pedidoRepository;

    public List<pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public pedido findById(int id) {
        return pedidoRepository.findById(id).get();
    }

    public pedido save(pedido pedido) {
        return pedidoRepository.save(pedido);
    }
    public void delete(int id) {
        pedidoRepository.deleteById(id);
    }

}
