package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.CargoController;
import grupo2.proyecto.full.stack1.Modelo.Cargo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CargoModelAssembler implements RepresentationModelAssembler<Cargo, EntityModel<Cargo>> {

    @Override
    public EntityModel<Cargo> toModel(Cargo cargo) {
        return EntityModel.of(cargo,
                linkTo(methodOn(CargoController.class).obtenerCargo(cargo.getId())).withSelfRel(),
                linkTo(methodOn(CargoController.class).listarCargos()).withRel("todosLosCargos"));
    }
}