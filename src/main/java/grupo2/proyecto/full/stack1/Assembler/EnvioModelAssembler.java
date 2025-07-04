package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.EnvioController;
import grupo2.proyecto.full.stack1.Modelo.envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<envio, EntityModel<envio>> {

    @Override
    public EntityModel<envio> toModel(envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).obtenerEnvio(envio.getId())).withSelfRel(),
                linkTo(methodOn(EnvioController.class).listarEnvio()).withRel("todosLosEnvios"));
    }
}
