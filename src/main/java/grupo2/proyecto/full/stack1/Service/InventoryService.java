package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Repository.inventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private inventoryRepository inventoryRepository;

    public String getAllInventory() {
        String output = "";
        for (Inventory inventory : inventoryRepository.findAll()) {
            output+="ID_Product: "+inventory.getId()+"\n";
            output+="Nombre: "+inventory.getNombre()+"\n";
            output+="componente: "+inventory.getComponente()+"\n";
            output+="stock: "+inventory.getStock()+"\n";
            output+="valor unitario: "+inventory.getValorUnitario()+"\n";
        }
        if(output.isEmpty()){
            return "No se han encontrado productos";
        }else{
            return output;
        }

    }





}
