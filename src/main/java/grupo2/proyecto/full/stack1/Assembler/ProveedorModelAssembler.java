package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.proveedorController;
import grupo2.proyecto.full.stack1.Modelo.proveedor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<proveedor, EntityModel<proveedor>> {

    @Override
    public EntityModel<proveedor> toModel(proveedor p) {
        return EntityModel.of(p,
                linkTo(methodOn(proveedorController.class).buscarProveedor(p.getId())).withSelfRel(),
                linkTo(methodOn(proveedorController.class).listarProveedor()).withRel("todosLosProveedores"));
    }
}
