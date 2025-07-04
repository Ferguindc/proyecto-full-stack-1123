package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.PedidoController;
import grupo2.proyecto.full.stack1.Modelo.pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<pedido, EntityModel<pedido>> {

    @Override
    public EntityModel<pedido> toModel(pedido p) {
        return EntityModel.of(p,
                linkTo(methodOn(PedidoController.class).obtenerPedido(p.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("todosLosPedidos"));
    }
}
