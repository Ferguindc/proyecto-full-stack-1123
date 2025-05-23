package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Repository.ProductRepository;
import grupo2.proyecto.full.stack1.Repository.inventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private inventoryRepository inventoryRepository;

    public List<Product> findAll() {
        return inventoryRepository.findAll();
    }

    public Product findById(int id) {
        return inventoryRepository.findById(id).get();
    }

    public Product save(Product producto) {
        return inventoryRepository.save(producto);
    }

    public void delete(int id) {
        inventoryRepository.deleteById(id);
    }




}
