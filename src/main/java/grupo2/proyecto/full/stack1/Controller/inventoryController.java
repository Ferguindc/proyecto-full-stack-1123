package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventory")
public class inventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> listarInventario() {
        try {
            List<Inventory> inventario = inventoryService.getAllInventory();
            if (inventario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "No hay registros de inventario."));
            }
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener el inventario."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInventario(@PathVariable int id) {
        try {
            Inventory item = inventoryService.getInventoryId(id);
            return ResponseEntity.ok(item);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarInventario(@RequestBody Inventory inventory) {
        try {
            Inventory nuevo = inventoryService.addProduct(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo agregar el producto."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInventario(@PathVariable int id, @RequestBody Inventory inventory) {
        try {
            Inventory actualizado = inventoryService.updateProduct(id, inventory);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el producto."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInventario(@PathVariable int id) {
        try {
            inventoryService.deleteProduct(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el producto."));
        }
    }
}
