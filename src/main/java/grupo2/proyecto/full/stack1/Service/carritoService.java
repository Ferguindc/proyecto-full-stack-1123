package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Repository.carritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class carritoService {

    @Autowired
    private carritoRepository CarritoRepository;

    public List<Carrito> listarCarritos() {
        return CarritoRepository.findAll();
    }

    public Carrito findById(int id) {
        return CarritoRepository.findById(id).get();
    }

    public Carrito save(Carrito carritos) {
        return CarritoRepository.save(carritos);
    }

    public void delete(int id) {
        CarritoRepository.deleteById(id);
    }
}
