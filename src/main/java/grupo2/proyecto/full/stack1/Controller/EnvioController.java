package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.envio;
import grupo2.proyecto.full.stack1.Service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/envio")
@Tag(name = "Envíos", description = "Operaciones relacionadas con el envío de productos")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(
            summary = "Listar envíos",
            description = "Obtiene todos los registros de envíos disponibles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente"),
                    @ApiResponse(responseCode = "404", description = "No hay registros de envíos", content = @Content)
            }
    )
    public ResponseEntity<?> listarEnvio() {
        List<envio> envios = envioService.findAll();
        if (envios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay registros de envíos."));
        }
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener envío por ID",
            description = "Obtiene un envío específico a partir de su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Envío encontrado"),
                    @ApiResponse(responseCode = "404", description = "Envío no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> obtenerEnvio(@PathVariable int id) {
        try {
            envio encontrado = envioService.findById(id);
            return ResponseEntity.ok(encontrado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el envío con ID: " + id));
        }
    }

    @PostMapping
    @Operation(
            summary = "Crear envío",
            description = "Registra un nuevo envío",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Envío creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el envío", content = @Content)
            }
    )
    public ResponseEntity<?> guardarEnvio(@RequestBody envio envio) {
        try {
            envio nuevo = envioService.save(envio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo guardar el envío."));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar envío",
            description = "Actualiza los datos de un envío existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Envío no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error al actualizar el envío", content = @Content)
            }
    )
    public ResponseEntity<?> actualizarEnvio(@PathVariable int id, @RequestBody envio envio) {
        try {
            envio existente = envioService.findById(id);
            existente.setNumeroEnvio(envio.getNumeroEnvio());
            existente.setDireccionEnvio(envio.getDireccionEnvio());
            existente.setCodigoEnvio(envio.getCodigoEnvio());

            envio actualizado = envioService.save(existente);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el envío con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el envío."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar envío",
            description = "Elimina un envío mediante su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Envío eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Envío no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error al eliminar el envío", content = @Content)
            }
    )
    public ResponseEntity<?> eliminarEnvio(@PathVariable int id) {
        try {
            envioService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Envío eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el envío con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el envío."));
        }
    }
}
