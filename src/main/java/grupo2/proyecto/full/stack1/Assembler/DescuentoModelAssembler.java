package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.DescuentoController;
import grupo2.proyecto.full.stack1.Modelo.descuento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class DescuentoModelAssembler implements RepresentationModelAssembler<descuento, EntityModel<descuento>> {


    @Override
    public EntityModel<descuento> toModel(descuento descuento) {
        return EntityModel.of(descuento,
                linkTo(methodOn(DescuentoController.class).obtenerDescuento(descuento.getId())).withSelfRel(),
                linkTo(methodOn(DescuentoController.class).listarDescuentos()).withRel("todosLosDescuentos"));
    }
}
