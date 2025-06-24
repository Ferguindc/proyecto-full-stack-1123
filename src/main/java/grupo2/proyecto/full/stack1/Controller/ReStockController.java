package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.reStock;
import grupo2.proyecto.full.stack1.Service.ReStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restock")
@Tag(name = "ReStock", description = "Gestión de registros de reposición de stock")
public class ReStockController {

    @Autowired
    private ReStockService reStockService;

    @GetMapping
    @Operation(summary = "Listar registros de reabastecimiento", description = "Muestra todos los registros de reStock")
    public ResponseEntity<List<reStock>> listarrestock() {
        List<reStock> reStocks = reStockService.findAll();
        if (reStocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reStocks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reStock por ID", description = "Busca un registro de reStock por su ID")
    public ResponseEntity<reStock> buscarrestock(@PathVariable int id) {
        try {
            reStock restock = reStockService.findById(id);
            return ResponseEntity.ok(restock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reStock", description = "Actualiza la información de un reStock existente")
    public ResponseEntity<reStock> actualizarCarrito(@PathVariable int id, @RequestBody reStock reStock) {
        try {
            reStock buscado = reStockService.findById(id);
            buscado.setStock(reStock.getStock());
            buscado.setFechaReStock(reStock.getFechaReStock());
            reStockService.save(buscado);
            return ResponseEntity.ok(buscado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reStock", description = "Elimina un registro de reStock por su ID")
    public ResponseEntity<reStock> eliminar(@PathVariable int id) {
        try {
            reStockService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
