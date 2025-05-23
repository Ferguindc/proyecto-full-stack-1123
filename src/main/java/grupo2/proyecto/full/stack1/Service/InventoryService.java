package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Repository.inventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private inventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryId(int id) {
        return inventoryRepository.findById(id).get();
    }

    public Inventory addProduct(Inventory inventario) {
        return inventoryRepository.save(inventario);
    }

    public void deleteProduct(int id) {
        inventoryRepository.deleteById(id);
    }




}
