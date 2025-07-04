package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.ReStockController;
import grupo2.proyecto.full.stack1.Modelo.reStock;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReStockModelAssembler implements RepresentationModelAssembler<reStock, EntityModel<reStock>> {

    @Override
    public EntityModel<reStock> toModel(reStock restock) {
        return EntityModel.of(restock,
                linkTo(methodOn(ReStockController.class).buscarRestock(restock.getId())).withSelfRel(),
                linkTo(methodOn(ReStockController.class).listarRestock()).withRel("todosLosReStock"));
    }
}
