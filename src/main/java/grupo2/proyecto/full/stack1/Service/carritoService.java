package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Repository.carritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class carritoService {

    @Autowired
    private carritoRepository CarritoRepository;

    public String pagarPedidos() {
        for (Carrito carrito : CarritoRepository.findAll()) {
            if (carrito.isPagado()) {
                carrito.setPagado(true);
                CarritoRepository.save(carrito);
            }
        }
        return "Todos los pedidos han sido pagados.";
    }

    public String eliminarPedido(int id) {
        if (CarritoRepository.existsById(id)) {
            CarritoRepository.deleteById(id);
            return "Pedido eliminado";
        } else {
            return "Pedido no encontrado";
        }
    }

    public String cancelarPago(int id) {
        if (CarritoRepository.existsById(id)) {
            Carrito carrito = CarritoRepository.findById(id).get();
            if (carrito.isPagado()) {
                carrito.setPagado(false);
                CarritoRepository.save(carrito);
                return "Pedido cancelado";
            } else {
                return "El pedido no fue pagado";
            }
        } else {
            return "Pedido no encontrado";
        }
    }

    public String actualizarPedido(int id, Carrito nuevoCarrito) {
        if (CarritoRepository.existsById(id)) {
            Carrito buscado = CarritoRepository.findById(id).get();
            buscado.setDescripcion(nuevoCarrito.getDescripcion());
            buscado.setTotal(nuevoCarrito.getTotal());
            buscado.setPagado(nuevoCarrito.isPagado());
            buscado.setCerrado(nuevoCarrito.isCerrado());
            CarritoRepository.save(buscado);
            return "Pedido actualizado.";
        } else {
            return "Pedido no encontrado.";
        }
    }


    public String cerrarCarrito(int id) {
        if (CarritoRepository.existsById(id)) {
            Carrito carrito = CarritoRepository.findById(id).get();
            carrito.setCerrado(true);
            CarritoRepository.save(carrito);
            return "Carrito cerrado.";
        } else {
            return "Carrito no encontrado.";
        }
    }

    public String getAllCarritos() {
        String output = "";
        for (Carrito carrito : CarritoRepository.findAll()) {
            output += "ID: " + carrito.getId() + "\n";
            output += "Descripción: " + carrito.getDescripcion() + "\n";
            output += "Total: " + carrito.getTotal() + "\n";
            output += "Pagado: " + carrito.isPagado() + "\n";
            output += "Cerrado: " + carrito.isCerrado() + "\n\n";
        }
        if (output.isEmpty()) {
            return "No se han encontrado carritos.";
        } else {
            return output;
        }
    }

}
