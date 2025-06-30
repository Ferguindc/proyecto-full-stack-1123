package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.proveedor;
import grupo2.proyecto.full.stack1.Repository.proveedorRepository;
import grupo2.proyecto.full.stack1.Service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedor")
@Tag(name = "Proveedores", description = "Gestión de proveedores del sistema")
public class proveedorController {

    @Autowired
    private proveedorRepository proveedorRepository;

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Obtiene todos los proveedores registrados")
    public ResponseEntity<?> listarProveedor() {
        List<proveedor> proveedores = proveedorRepository.findAll();
        if (proveedores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    @Operation(summary = "Crear proveedor", description = "Registra un nuevo proveedor")
    public ResponseEntity<?> crearProveedor(@RequestBody proveedor proveedor) {
        try {
            proveedor proveedorNuevo = proveedorRepository.save(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorNuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el proveedor."));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar proveedor por ID", description = "Busca un proveedor según su ID")
    public ResponseEntity<?> buscarProveedor(@PathVariable int id) {
        try {
            proveedor proveedor = proveedorService.findById(id);
            return ResponseEntity.ok(proveedor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el proveedor con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proveedor", description = "Actualiza los datos de un proveedor existente")
    public ResponseEntity<?> actualizarProveedor(@PathVariable int id, @RequestBody proveedor proveedor) {
        try {
            proveedor buscado = proveedorService.findById(id);
            buscado.setDireccionProveedor(proveedor.getDireccionProveedor());
            buscado.setNombreProveedor(proveedor.getNombreProveedor());
            buscado.setTelefonoProveedor(proveedor.getTelefonoProveedor());
            buscado.setEmailProveedor(proveedor.getEmailProveedor());
            proveedorRepository.save(buscado);
            return ResponseEntity.ok(buscado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo actualizar, proveedor no encontrado con ID: " + id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema")
    public ResponseEntity<?> eliminarProveedor(@PathVariable int id) {
        try {
            proveedorService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado correctamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo eliminar, proveedor no encontrado con ID: " + id));
        }
    }
}
