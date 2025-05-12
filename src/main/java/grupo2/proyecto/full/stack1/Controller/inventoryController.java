package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/inventory")

public class inventoryController {
    @Autowired
    private InventoryService inventoryService;


    @GetMapping
    public String getAllinventory() {

        return inventoryService.getAllInventory();

    }
}
