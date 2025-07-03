package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.reStock;
import grupo2.proyecto.full.stack1.Service.ReStockService;
import grupo2.proyecto.full.stack1.Assembler.ReStockModelAssembler;

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
@RequestMapping("/restock")
@Tag(name = "ReStock", description = "Gestión de registros de reposición de stock")
public class ReStockController {

    @Autowired
    private ReStockService reStockService;

    @Autowired
    private ReStockModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar registros de reabastecimiento",
            description = "Muestra todos los registros de reStock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de registros obtenida"),
                    @ApiResponse(responseCode = "204", description = "No hay registros de reStock", content = @Content)
            }
    )
    public ResponseEntity<?> listarRestock() {
        List<reStock> reStocks = reStockService.findAll();

        if (reStocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<reStock>> reStocksConLinks = reStocks.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<reStock>> collectionModel = CollectionModel.of(reStocksConLinks,
                linkTo(methodOn(ReStockController.class).listarRestock()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reStock por ID",
            description = "Busca un registro de reStock por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registro encontrado"),
                    @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> buscarRestock(@PathVariable int id) {
        try {
            reStock restock = reStockService.findById(id);
            return ResponseEntity.ok(assembler.toModel(restock));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el registro con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar reStock",
            description = "Actualiza la información de un reStock existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registro actualizado"),
                    @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> actualizarRestock(@PathVariable int id, @RequestBody reStock reStock) {
        try {
            reStock buscado = reStockService.findById(id);
            buscado.setStock(reStock.getStock());
            buscado.setFechaReStock(reStock.getFechaReStock());
            reStock actualizado = reStockService.save(buscado);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo actualizar, registro no encontrado con ID: " + id));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar reStock",
            description = "Elimina un registro de reStock por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            reStockService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Registro eliminado correctamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se pudo eliminar, registro no encontrado con ID: " + id));
        }
    }
}
