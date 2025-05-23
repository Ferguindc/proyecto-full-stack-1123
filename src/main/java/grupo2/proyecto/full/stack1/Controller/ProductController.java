package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/producto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> listarProduct() {
        List<Product> productos = productService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "No hay productos registrados."));
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody Product producto) {
        Product productoNuevo = productService.save(producto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", 201, "message", "Producto creado exitosamente", "data", productoNuevo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProductoPorId(@PathVariable int id) {
        try {
            Product producto = productService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "Producto no encontrada con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody Product producto) {
        try {
            Product buscado = productService.findById(id);
            buscado.setId(id);
            buscado.setName(producto.getName());
            buscado.setDescription(producto.getDescription());
            buscado.setPrice(producto.getPrice());
            buscado.setStock(producto.getStock());
            productService.save(buscado);
            return ResponseEntity.ok(Map.of("status", 200, "message", "Producto actualizado exitosamente", "data", buscado));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "No se pudo actualizar, producto no encontrado con ID: " + id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok(Map.of("status", 200, "message", "Producto eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "No se pudo eliminar, producto no encontrada con ID: " + id));
        }
    }

}
