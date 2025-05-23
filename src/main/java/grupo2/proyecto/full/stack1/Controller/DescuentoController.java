package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.descuento;
import grupo2.proyecto.full.stack1.Service.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/descuento")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    @GetMapping
    public ResponseEntity<?> listarDescuentos() {
        List<descuento> descuentos = descuentoService.findAll();
        if (descuentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay descuentos registrados."));
        }
        return ResponseEntity.ok(descuentos);
    }

    @GetMapping("/{id}")
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