package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Sucursal;
import grupo2.proyecto.full.stack1.Service.SucursalService;
import grupo2.proyecto.full.stack1.Assembler.SucursalModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/sucursal")
@Tag(name = "Sucursales", description = "Administración de sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar sucursales",
            description = "Muestra todas las sucursales registradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida"),
                    @ApiResponse(responseCode = "204", description = "No hay sucursales registradas", content = @Content)
            }
    )
    public ResponseEntity<?> listarSucursal() {
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Sucursal>> sucursalesConLinks = sucursales.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(sucursalesConLinks,
                linkTo(methodOn(SucursalController.class).listarSucursal()).withSelfRel()));
    }

    @PostMapping
    @Operation(
            summary = "Crear sucursal",
            description = "Registra una nueva sucursal",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear la sucursal", content = @Content)
            }
    )
    public ResponseEntity<?> guardarSucursal(@RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalNueva = sucursalService.save(sucursal);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(assembler.toModel(sucursalNueva));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", "No se pudo crear la sucursal."));
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar sucursal por ID",
            description = "Obtiene los datos de una sucursal específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
            }
    )
    public ResponseEntity<?> buscarSucursalPorId(@PathVariable int id) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            return ResponseEntity.ok(assembler.toModel(sucursal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Sucursal no encontrada con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar sucursal",
            description = "Modifica la información de una sucursal existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucursal actualizada"),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
            }
    )
    public ResponseEntity<?> actualizarSucursal(@PathVariable int id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal buscada = sucursalService.findById(id);
            buscada.setDireccionSucursal(sucursal.getDireccionSucursal());
            buscada.setNombreSucursal(sucursal.getNombreSucursal());
            Sucursal actualizada = sucursalService.save(buscada);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo actualizar, sucursal no encontrada con ID: " + id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar sucursal",
            description = "Elimina una sucursal del sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucursal eliminada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
            }
    )
    public ResponseEntity<?> eliminarSucursal(@PathVariable int id) {
        try {
            sucursalService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Sucursal eliminada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo eliminar, sucursal no encontrada con ID: " + id));
        }
    }
}
