package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Service.carritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/carrito")
@Tag(name = "Carro de compras", description = "Carrito relacionado con las compras")
public class CarritoController {

    @Autowired
    private carritoService carritoService;


    @GetMapping
    @Operation(summary = " Obtener todos los Carros", description = "obtiene una lista de todos Carritos de compra")
    public ResponseEntity<?> listarCarritos() {
        List<Carrito> carritos = carritoService.listarCarritos();
        if (carritos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay carritos registrados."));
        }
        return ResponseEntity.ok(carritos);
    }


    @GetMapping("/{id}")
    @Operation(summary = " Obtener carrito por id", description = "obtiene una lista de los carritos por ID")
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
    @Operation(summary = " Publicar un carrito ", description = "Publicar un carrito nuevo")
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
    @Operation(summary = " Editar un carrito", description = "Editar un carrito por Id")
    public ResponseEntity<?> actualizarCarrito(@PathVariable int id, @RequestBody Carrito carritoActualizado) {
        try {
            Carrito existente = carritoService.findById(id);
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
    @Operation(summary = " Borrar un carrito", description = "Borrar un carrito por Id")
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
