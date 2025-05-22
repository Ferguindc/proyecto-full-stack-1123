package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.reStock;
import grupo2.proyecto.full.stack1.Service.ReStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
