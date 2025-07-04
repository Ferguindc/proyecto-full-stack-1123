package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.proveedor;
import grupo2.proyecto.full.stack1.Repository.proveedorRepository;
import grupo2.proyecto.full.stack1.Service.ProveedorService;
import grupo2.proyecto.full.stack1.Assembler.ProveedorModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/proveedor")
@Tag(name = "Proveedores", description = "Gestión de proveedores del sistema")
public class proveedorController {

    @Autowired
    private proveedorRepository proveedorRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar proveedores",
            description = "Obtiene todos los proveedores registrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida"),
                    @ApiResponse(responseCode = "204", description = "No hay proveedores registrados", content = @Content)
            }
    )
    public ResponseEntity<?> listarProveedor() {
        List<proveedor> proveedores = proveedorRepository.findAll();
        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<proveedor>> proveedoresConLinks = proveedores.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(proveedoresConLinks,
                linkTo(methodOn(proveedorController.class).listarProveedor()).withSelfRel()));
    }

    @PostMapping
    @Operation(
            summary = "Crear proveedor",
            description = "Registra un nuevo proveedor",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el proveedor", content = @Content)
            }
    )
    public ResponseEntity<?> crearProveedor(@RequestBody proveedor proveedor) {
        try {
            proveedor proveedorNuevo = proveedorRepository.save(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(proveedorNuevo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el proveedor."));
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar proveedor por ID",
            description = "Busca un proveedor según su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
                    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> buscarProveedor(@PathVariable int id) {
        try {
            proveedor p = proveedorService.findById(id);
            return ResponseEntity.ok(assembler.toModel(p));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el proveedor con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar proveedor",
            description = "Actualiza los datos de un proveedor existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proveedor actualizado"),
                    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> actualizarProveedor(@PathVariable int id, @RequestBody proveedor proveedor) {
        try {
            proveedor buscado = proveedorService.findById(id);
            buscado.setDireccionProveedor(proveedor.getDireccionProveedor());
            buscado.setNombreProveedor(proveedor.getNombreProveedor());
            buscado.setTelefonoProveedor(proveedor.getTelefonoProveedor());
            buscado.setEmailProveedor(proveedor.getEmailProveedor());

            proveedor actualizado = proveedorRepository.save(buscado);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo actualizar, proveedor no encontrado con ID: " + id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar proveedor",
            description = "Elimina un proveedor del sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
            }
    )
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
