package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.descuento;
import grupo2.proyecto.full.stack1.Service.DescuentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/descuento")
@Tag(name = "Descuentos", description = "Operaciones relacionadas con descuentos aplicables a pedidos o productos")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    @GetMapping
    @Operation(summary = "Listar todos los descuentos", description = "Obtiene una lista de todos los descuentos registrados en el sistema.")
    public ResponseEntity<?> listarDescuentos() {
        List<descuento> descuentos = descuentoService.findAll();
        if (descuentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay descuentos registrados."));
        }
        return ResponseEntity.ok(descuentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener descuento por ID", description = "Retorna la información de un descuento específico utilizando su ID.")
    public ResponseEntity<?> obtenerDescuento(@PathVariable int id) {
        try {
            descuento d = descuentoService.findById(id);
            return ResponseEntity.ok(d);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Descuento no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo descuento", description = "Registra un nuevo descuento en el sistema con los datos proporcionados.")
    public ResponseEntity<?> crearDescuento(@RequestBody descuento nuevoDescuento) {
        try {
            descuento descuentoGuardado = descuentoService.save(nuevoDescuento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(descuentoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el descuento."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un descuento", description = "Actualiza la información de un descuento existente con el ID proporcionado.")
    public ResponseEntity<?> actualizarDescuento(@PathVariable int id, @RequestBody descuento descuentoActualizado) {
        try {
            descuento descuentoExistente = descuentoService.findById(id);

            descuentoExistente.setTipodDescuento(descuentoActualizado.getTipodDescuento());
            descuentoExistente.setDescuento(descuentoActualizado.getDescuento());

            descuento descuentoGuardado = descuentoService.save(descuentoExistente);
            return ResponseEntity.ok(descuentoGuardado);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Descuento no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el descuento."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un descuento", description = "Elimina un descuento existente del sistema utilizando su ID.")
    public ResponseEntity<?> eliminarDescuento(@PathVariable int id) {
        try {
            descuentoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Descuento eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el descuento con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el descuento."));
        }
    }
}
