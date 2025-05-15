package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Repository.carritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class carritoService {

    @Autowired
    private carritoRepository CarritoRepository;

    public String addCarrito(Carrito carrito) {
        CarritoRepository.save(carrito);
        return "Carrito agregado correctamente";
    }

    public String getAllCarritos() {
        String output = "";
        for (Carrito carrito : CarritoRepository.findAll()) {
            output += "ID: " + carrito.getId() + "\n";
            output += "Descripción: " + carrito.getDescripcion() + "\n";
            output += "Total: " + carrito.getTotal() + "\n";
            output += "Estado: " + carrito.getEstado() + "\n\n";
        }
        if (output.isEmpty()) {
            return "No se han encontrado carritos.";
        } else {
            return output;
        }
    }

    public String deletePedido(int id) {
        if (CarritoRepository.existsById(id)) {
            CarritoRepository.deleteById(id);
            return "Pedido eliminado";
        } else {
            return "Pedido no encontrado";
        }
    }

    public String updatePedido(int id, Carrito nuevoCarrito) {
        if (CarritoRepository.existsById(id)) {
            Carrito buscado = CarritoRepository.findById(id).get();
            buscado.setDescripcion(nuevoCarrito.getDescripcion());
            buscado.setTotal(nuevoCarrito.getTotal());
            buscado.setEstado(nuevoCarrito.getEstado());
            CarritoRepository.save(buscado);
            return "Pedido actualizado.";
        } else {
            return "Pedido no encontrado.";
        }
    }
}
