package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.ProductController;
import grupo2.proyecto.full.stack1.Modelo.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductController.class).obtenerProducto(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).listarProductos()).withRel("todosLosProductos"));
    }
}
