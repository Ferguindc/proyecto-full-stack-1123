package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Sucursal;
import grupo2.proyecto.full.stack1.Service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sucursal")
@Tag(name = "Sucursales", description = "Administración de sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Muestra todas las sucursales registradas")
    public ResponseEntity<?> listarSucursal() {
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "No hay sucursales registradas."));
        }
        return ResponseEntity.ok(sucursales);
    }

    @PostMapping
    @Operation(summary = "Crear sucursal", description = "Registra una nueva sucursal")
    public ResponseEntity<?> guardarSucursal(@RequestBody Sucursal sucursal) {
        Sucursal sucursalNuevo = sucursalService.save(sucursal);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", 201, "message", "Sucursal creada exitosamente", "data", sucursalNuevo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Obtiene los datos de una sucursal específica")
    public ResponseEntity<?> buscarSucursalPorId(@PathVariable int id) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "Sucursal no encontrada con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Modifica la información de una sucursal existente")
    public ResponseEntity<?> actualizarSucursal(@PathVariable int id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal buscado = sucursalService.findById(id);
            buscado.setId(id);
            buscado.setDireccionSucursal(sucursal.getDireccionSucursal());
            buscado.setNombreSucursal(sucursal.getNombreSucursal());
            sucursalService.save(buscado);
            return ResponseEntity.ok(Map.of("status", 200, "message", "Sucursal actualizada exitosamente", "data", buscado));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "No se pudo actualizar, sucursal no encontrada con ID: " + id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal del sistema")
    public ResponseEntity<?> eliminarSucursal(@PathVariable int id) {
        try {
            sucursalService.delete(id);
            return ResponseEntity.ok(Map.of("status", 200, "message", "Sucursal eliminada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", 404, "message", "No se pudo eliminar, sucursal no encontrada con ID: " + id));
        }
    }
}
