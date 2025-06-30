package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.descuento;
import grupo2.proyecto.full.stack1.Service.DescuentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar todos los descuentos", description = "Obtiene una lista de todos los descuentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de descuentos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay descuentos registrados")
    })
    public ResponseEntity<?> listarDescuentos() {
        List<descuento> descuentos = descuentoService.findAll();
        if (descuentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay descuentos registrados."));
        }
        return ResponseEntity.ok(descuentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener descuento por ID", description = "Obtiene un descuento específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento encontrado"),
            @ApiResponse(responseCode = "404", description = "Descuento no encontrado")
    })
    public ResponseEntity<?> obtenerDescuento(
            @Parameter(description = "ID del descuento", required = true) @PathVariable int id) {
        try {
            descuento d = descuentoService.findById(id);
            return ResponseEntity.ok(d);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Descuento no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo descuento", description = "Registra un nuevo descuento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Descuento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> crearDescuento(@RequestBody descuento nuevoDescuento) {
        try {
            descuento descuentoGuardado = descuentoService.save(nuevoDescuento);
            return ResponseEntity.status(HttpStatus.CREATED).body(descuentoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el descuento."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un descuento", description = "Actualiza un descuento existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar"),
            @ApiResponse(responseCode = "404", description = "Descuento no encontrado")
    })
    public ResponseEntity<?> actualizarDescuento(
            @Parameter(description = "ID del descuento", required = true) @PathVariable int id,
            @RequestBody descuento descuentoActualizado) {
        try {
            descuento existente = descuentoService.findById(id);
            existente.setTipodDescuento(descuentoActualizado.getTipodDescuento());
            existente.setDescuento(descuentoActualizado.getDescuento());
            descuento actualizado = descuentoService.save(existente);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Descuento no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el descuento."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar descuento", description = "Elimina un descuento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Descuento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar")
    })
    public ResponseEntity<?> eliminarDescuento(
            @Parameter(description = "ID del descuento", required = true) @PathVariable int id) {
        try {
            descuentoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Descuento eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Descuento no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el descuento."));
        }
    }
}
