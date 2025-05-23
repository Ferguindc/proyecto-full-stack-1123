package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Repository.ProductRepository;
import grupo2.proyecto.full.stack1.Repository.carritoRepository;
import grupo2.proyecto.full.stack1.Repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

            CarritoRepository.save(buscado);
            return "Carrito actualizado.";
        } else {
            return "Carrito no encontrado.";
        }
    }

    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    public Product save(Product producto) {
        return productRepository.save(producto);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
