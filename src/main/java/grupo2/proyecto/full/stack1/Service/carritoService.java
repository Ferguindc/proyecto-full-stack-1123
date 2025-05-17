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

    public String getCarritoById(int id) {
        String output="";
        if(CarritoRepository.existsById(id)){
            Carrito carrito = CarritoRepository.findById(id).get();
            output+="ID del Carrito: "+carrito.getId()+"\n";
            output+="Nombre: "+carrito.getDescripcion()+"\n";
            output+="Descripción: "+carrito.getTotal()+"\n";
            output+="Estado: "+carrito.getEstado()+"\n";
            return output;
        }else{
            return "No se ha encontrado el carrito";
        }
    }

    public String deleteCarrito(int id) {
        if (CarritoRepository.existsById(id)) {
            CarritoRepository.deleteById(id);
            return "Carrito eliminado";
        } else {
            return "Carrito no encontrado";
        }
    }

    public String updateCarrito(int id, Carrito nuevoCarrito) {
        if (CarritoRepository.existsById(id)) {
            Carrito buscado = CarritoRepository.findById(id).get();
            buscado.setDescripcion(nuevoCarrito.getDescripcion());
            buscado.setTotal(nuevoCarrito.getTotal());
            buscado.setEstado(nuevoCarrito.getEstado());
            CarritoRepository.save(buscado);
            return "Carrito actualizado.";
        } else {
            return "Carrito no encontrado.";
        }
    }
}
