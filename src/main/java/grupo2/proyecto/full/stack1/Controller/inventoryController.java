package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Service.InventoryService;
import grupo2.proyecto.full.stack1.Assembler.InventoryModelAssembler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.*;
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
@RequestMapping("/inventario")
@Tag(name = "Inventario", description = "Gestión de stock y productos en inventario")
public class inventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar inventario", description = "Obtiene todos los productos del inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "404", description = "Inventario vacío", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<?> listarInventario() {
        try {
            List<Inventory> inventario = inventoryService.getAllInventory();
            if (inventario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "No hay registros de inventario."));
            }

            List<EntityModel<Inventory>> inventarioConLinks = inventario.stream()
                    .map(assembler::toModel)
                    .toList();

            return ResponseEntity.ok(CollectionModel.of(inventarioConLinks,
                    linkTo(methodOn(inventoryController.class).listarInventario()).withSelfRel()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener el inventario."));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto del inventario mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    public ResponseEntity<?> obtenerInventario(@PathVariable int id) {
        try {
            Inventory item = inventoryService.getInventoryId(id);
            return ResponseEntity.ok(assembler.toModel(item));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Agregar producto al inventario", description = "Agrega un nuevo producto al inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al agregar el producto", content = @Content)
    })
    public ResponseEntity<?> agregarInventario(@RequestBody Inventory inventory) {
        try {
            Inventory nuevo = inventoryService.addProduct(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo agregar el producto."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Actualiza el stock de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al actualizar", content = @Content)
    })
    public ResponseEntity<?> actualizarInventario(@PathVariable int id, @RequestBody Inventory inventarioActualizado) {
        try {
            Inventory inventarioExistente = inventoryService.getInventoryId(id);
            inventarioExistente.setStock(inventarioActualizado.getStock());
            Inventory guardado = inventoryService.addProduct(inventarioExistente);
            return ResponseEntity.ok(assembler.toModel(guardado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Inventario no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el inventario."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del inventario mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al eliminar", content = @Content)
    })
    public ResponseEntity<?> eliminarInventario(@PathVariable int id) {
        try {
            inventoryService.deleteProduct(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el producto."));
        }
    }
}
