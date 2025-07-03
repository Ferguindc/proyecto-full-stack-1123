package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Service.ProductService;
import grupo2.proyecto.full.stack1.Assembler.ProductModelAssembler;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/producto")
@Tag(name = "Productos", description = "Gestión de productos disponibles en la tienda")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados"),
            @ApiResponse(responseCode = "404", description = "No hay productos registrados", content = @Content)
    })
    public ResponseEntity<?> listarProductos() {
        List<Product> productos = productService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay productos registrados."));
        }

        List<EntityModel<Product>> productosConLinks = productos.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(productosConLinks,
                linkTo(methodOn(ProductController.class).listarProductos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene la información de un producto mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    public ResponseEntity<?> obtenerProducto(@PathVariable int id) {
        try {
            Product producto = productService.findById(id);
            return ResponseEntity.ok(assembler.toModel(producto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Error al crear el producto", content = @Content)
    })
    public ResponseEntity<?> crearProducto(@RequestBody Product nuevoProducto) {
        try {
            Product productoGuardado = productService.save(nuevoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(productoGuardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el producto."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza la información de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al actualizar", content = @Content)
    })
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody Product productoActualizado) {
        try {
            Product productoExistente = productService.findById(id);
            productoExistente.setName(productoActualizado.getName());
            productoExistente.setDescription(productoActualizado.getDescription());
            productoExistente.setPrice(productoActualizado.getPrice());
            productoExistente.setInventario(productoActualizado.getInventario());

            Product productoGuardado = productService.save(productoExistente);
            return ResponseEntity.ok(assembler.toModel(productoGuardado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Producto no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el producto."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al eliminar", content = @Content)
    })
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        try {
            productService.findById(id);
            productService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el producto con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el producto."));
        }
    }
}
