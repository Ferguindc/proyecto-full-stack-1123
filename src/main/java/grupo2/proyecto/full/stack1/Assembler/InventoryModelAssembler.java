package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.inventoryController;
import grupo2.proyecto.full.stack1.Modelo.Inventory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventoryModelAssembler implements RepresentationModelAssembler<Inventory, EntityModel<Inventory>> {

    @Override
    public EntityModel<Inventory> toModel(Inventory inventory) {
        return EntityModel.of(inventory,
                linkTo(methodOn(inventoryController.class).obtenerInventario(inventory.getId())).withSelfRel(),
                linkTo(methodOn(inventoryController.class).listarInventario()).withRel("todosLosProductos"));
    }
}
