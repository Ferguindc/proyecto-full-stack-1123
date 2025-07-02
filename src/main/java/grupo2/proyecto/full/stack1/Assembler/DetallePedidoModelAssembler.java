package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.detallePedidoController;
import grupo2.proyecto.full.stack1.Modelo.detallePedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetallePedidoModelAssembler implements RepresentationModelAssembler<detallePedido, EntityModel<detallePedido>> {

    @Override
    public EntityModel<detallePedido> toModel(detallePedido detalle) {
        return EntityModel.of(detalle,
                linkTo(methodOn(detallePedidoController.class).obtenerDetalle(detalle.getId())).withSelfRel(),
                linkTo(methodOn(detallePedidoController.class).listarDetalles()).withRel("todosLosDetalles"));
    }
}
