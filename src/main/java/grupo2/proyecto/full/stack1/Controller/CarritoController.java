package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Service.carritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private carritoService carritoService;


    @GetMapping
    public ResponseEntity<?> listarCarritos() {
        List<Carrito> carritos = carritoService.findAll();
        if (carritos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay carritos registrados."));
        }
        return ResponseEntity.ok(carritos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCarrito(@PathVariable int id) {
        try {
            Carrito carrito = carritoService.findById(id);
            return ResponseEntity.ok(carrito);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Carrito no encontrado con ID: " + id));
        }
    }


    @PostMapping
    public ResponseEntity<?> crearCarrito(@RequestBody Carrito nuevoCarrito) {
        try {
            Carrito guardado = carritoService.save(nuevoCarrito);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el carrito."));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCarrito(@PathVariable int id, @RequestBody Carrito carritoActualizado) {
        try {
            Carrito existente = carritoService.findById(id);

            existente.setCantidad(carritoActualizado.getCantidad());
            existente.setCliente(carritoActualizado.getCliente());

            Carrito actualizado = carritoService.save(existente);
            return ResponseEntity.ok(actualizado);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Carrito no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el carrito."));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCarrito(@PathVariable int id) {
        try {
            carritoService.findById(id); // Validar existencia
            carritoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Carrito eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Carrito no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el carrito."));
        }
    }
}
