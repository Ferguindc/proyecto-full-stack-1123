package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Cargo;
import grupo2.proyecto.full.stack1.Service.cargoService;
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
@RequestMapping("/cargo")
@Tag(name = "Cargos", description = "Cargos relacionados con Empleados")
public class CargoController {

    @Autowired
    private cargoService cargoService;

    @GetMapping
    @Operation(summary = "Obtener todos los cargos", description = "Obtiene una lista de todos los cargos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cargos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay cargos registrados")
    })
    public ResponseEntity<?> listarCargos() {
        List<Cargo> cargos = cargoService.findAll();
        if (cargos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay cargos registrados."));
        }
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cargo por ID", description = "Obtiene un cargo específico dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo encontrado"),
            @ApiResponse(responseCode = "404", description = "Cargo no encontrado")
    })
    public ResponseEntity<?> obtenerCargo(
            @Parameter(description = "ID del cargo a obtener", required = true)
            @PathVariable int id) {
        try {
            Cargo cargo = cargoService.findById(id);
            return ResponseEntity.ok(cargo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cargo no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cargo", description = "Crea y guarda un nuevo cargo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cargo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el cargo")
    })
    public ResponseEntity<?> crearCargo(
            @RequestBody Cargo nuevoCargo) {
        try {
            Cargo guardado = cargoService.save(nuevoCargo);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el cargo."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cargo existente", description = "Actualiza los datos de un cargo dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cargo no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar el cargo")
    })
    public ResponseEntity<?> actualizarCargo(
            @Parameter(description = "ID del cargo a actualizar", required = true)
            @PathVariable int id,
            @RequestBody Cargo cargoActualizado) {
        try {
            Cargo existente = cargoService.findById(id);

            existente.setNombre(cargoActualizado.getNombre());
            existente.setSalario(cargoActualizado.getSalario());

            Cargo actualizado = cargoService.save(existente);
            return ResponseEntity.ok(actualizado);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cargo no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el cargo."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cargo", description = "Elimina un cargo dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cargo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar el cargo")
    })
    public ResponseEntity<?> eliminarCargo(
            @Parameter(description = "ID del cargo a eliminar", required = true)
            @PathVariable int id) {
        try {
            Cargo existente = cargoService.findById(id);
            cargoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Cargo eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cargo no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el cargo."));
        }
    }
}
