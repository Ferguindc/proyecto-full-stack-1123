package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.reStock;
import grupo2.proyecto.full.stack1.Service.ReStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/restock"})
public class ReStockController {
    @Autowired
    private ReStockService reStockService;

    @GetMapping
    public ResponseEntity<List<reStock>> listarrestock(){
        List<reStock> reStocks = reStockService.findAll();
        if(reStocks.isEmpty()){
            return ResponseEntity.noContent().build();

        }return ResponseEntity.ok(reStocks);

    }
    @GetMapping("/{id}")
    public ResponseEntity<reStock> buscarrestock(@PathVariable int id){
        try{
            reStock restock = reStockService.findById(id);
            return ResponseEntity.ok(restock);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<reStock> actualizarCarrito(@PathVariable int id, @RequestBody reStock reStock) {
        try {
            reStock buscado = reStockService.findById(id);
            reStock.setStock(buscado.getStock());
            reStock.setFechaReStock(buscado.getFechaReStock());
            reStockService.save(reStock);
            return ResponseEntity.ok(buscado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<reStock> eliminar(@PathVariable int id) {
        try{
            reStockService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
