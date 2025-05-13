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
    public String getInventoryId(int id) {
        String output="";
        if(inventoryRepository.existsById(id)){
            Inventory inventory = inventoryRepository.findById(id).get();
            output+="ID_Product: "+inventory.getId()+"\n";
            output+="Nombre: "+inventory.getNombre()+"\n";
            output+="componente: "+inventory.getComponente()+"\n";
            output+="stock: "+inventory.getStock()+"\n";
            output+="valor unitario: "+inventory.getValorUnitario()+"\n";
            return output;
        }else{
            return "No se ha encontrado el producto";
        }
    }
    public String addProduct(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "inventario añadido";
    }

    public String deleteProduct(int id) {
        if(inventoryRepository.existsById(id)){
            inventoryRepository.deleteById(id);
            return "inventario eliminado";
        }else{
            return "No se ha encontrado el inventario";
        }
    }

    public String updateProduct(int id,Inventory inventory) {
        if(inventoryRepository.existsById(id)){
            Inventory buscado = inventoryRepository.findById(id).get();
            buscado.setNombre(inventory.getNombre());
            buscado.setComponente(inventory.getComponente());
            buscado.setValorUnitario(inventory.getValorUnitario());
            buscado.setStock(inventory.getStock());
            inventoryRepository.save(buscado);
            return "inventario actualizado";
        }else{
            return "No se ha encontrado el inventario";
        }
    }




}
