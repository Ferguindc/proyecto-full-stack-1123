package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/inventory")

public class inventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public String getAllInventory(@PathVariable int id) {

        return inventoryService.getInventoryId(id);
    }

    @PostMapping
    public String addInventory(@RequestBody Inventory inventory) {
        return inventoryService.addProduct(inventory);
    }

    @DeleteMapping("/{id}")
    public String deleteInventory(@PathVariable int id) {
        return inventoryService.deleteProduct(id);

    }

    @PutMapping("/{id}")
    public String updateInventory(@PathVariable int id, @RequestBody Inventory inventory) {
        return inventoryService.updateProduct(id, inventory);
    }

}
