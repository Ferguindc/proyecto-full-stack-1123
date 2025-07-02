package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Service.carritoService;
import grupo2.proyecto.full.stack1.Assembler.CarritoModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/carrito")
@Tag(name = "Carro de compras", description = "Carrito relacionado con las compras")
public class CarritoController {

    @Autowired
    private carritoService carritoService;

    @Autowired
    private CarritoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todos los carritos", description = "Obtiene una lista de todos los carritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carritos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay carritos registrados")
    })
    public ResponseEntity<?> listarCarritos() {
        List<Carrito> carritos = carritoService.listarCarritos();
        if (carritos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay carritos registrados."));
        }

        List<EntityModel<Carrito>> carritosConLinks = carritos.stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Carrito>> collectionModel = CollectionModel.of(carritosConLinks,
                linkTo(methodOn(CarritoController.class).listarCarritos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener carrito por ID", description = "Obtiene un carrito específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    public ResponseEntity<?> obtenerCarrito(
            @Parameter(description = "ID del carrito", required = true) @PathVariable int id) {
        try {
            Carrito carrito = carritoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(carrito));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Carrito no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo carrito", description = "Registra un nuevo carrito en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> crearCarrito(@RequestBody Carrito nuevoCarrito) {
        try {
            Carrito guardado = carritoService.save(nuevoCarrito);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el carrito."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar carrito", description = "Actualiza un carrito existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    public ResponseEntity<?> actualizarCarrito(
            @Parameter(description = "ID del carrito", required = true) @PathVariable int id,
            @RequestBody Carrito carritoActualizado) {
        try {
            Carrito existente = carritoService.findById(id);
            existente.setCliente(carritoActualizado.getCliente());
            Carrito actualizado = carritoService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Carrito no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el carrito."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar carrito", description = "Elimina un carrito por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar")
    })
    public ResponseEntity<?> eliminarCarrito(
            @Parameter(description = "ID del carrito", required = true) @PathVariable int id) {
        try {
            carritoService.findById(id);
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
