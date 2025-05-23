package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/producto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> listarProductos() {
        List<Product> productos = productService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay productos registrados."));
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable int id) {
        try {
            Product producto = productService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Product nuevoProducto) {
        try {
            Product productoGuardado = productService.save(nuevoProducto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el producto."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody Product productoActualizado) {
        try {
            Product productoExistente = productService.findById(id);
            productoExistente.setName(productoActualizado.getName());
            productoExistente.setDescription(productoActualizado.getDescription());
            productoExistente.setPrice(productoActualizado.getPrice());
            productoExistente.setInventario(productoActualizado.getInventario());
            Product productoGuardado = productService.save(productoExistente);
            return ResponseEntity.ok(productoGuardado);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Producto no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el producto."));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        try {
            productService.findById(id); // Validar que existe
            productService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el producto."));
        }
    }
}
