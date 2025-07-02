package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.detallePedido;
import grupo2.proyecto.full.stack1.Service.DetallePedidoService;
import grupo2.proyecto.full.stack1.Assembler.DetallePedidoModelAssembler;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/detalle-pedido")
@Tag(name = "Detalles de Pedido", description = "Gestión de detalles asociados a pedidos de productos")
public class detallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private DetallePedidoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de pedidos", description = "Obtiene una lista con todos los detalles de pedidos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay detalles registrados")
    })
    public ResponseEntity<?> listarDetalles() {
        List<detallePedido> detalles = detallePedidoService.findAll();
        if (detalles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay detalles de pedidos registrados."));
        }

        List<EntityModel<detallePedido>> detallesConLinks = detalles.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(detallesConLinks,
                linkTo(methodOn(detallePedidoController.class).listarDetalles()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de pedido por ID", description = "Obtiene un detalle de pedido específico a partir de su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<?> obtenerDetalle(@PathVariable int id) {
        try {
            detallePedido detalle = detallePedidoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(detalle));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Detalle de pedido no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de pedido", description = "Registra un nuevo detalle para un pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> crearDetalle(@RequestBody detallePedido nuevoDetalle) {
        try {
            detallePedido guardado = detallePedidoService.save(nuevoDetalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el detalle de pedido."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un detalle de pedido", description = "Modifica un detalle de pedido existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<?> actualizarDetalle(@PathVariable int id, @RequestBody detallePedido detalleActualizado) {
        try {
            detallePedido existente = detallePedidoService.findById(id);
            existente.setCantidadProductos(detalleActualizado.getCantidadProductos());
            existente.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
            existente.setProduct(detalleActualizado.getProduct());
            existente.setPedido(detalleActualizado.getPedido());
            detallePedido actualizado = detallePedidoService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Detalle de pedido no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el detalle de pedido."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle de pedido", description = "Elimina un detalle de pedido utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar")
    })
    public ResponseEntity<?> eliminarDetalle(@PathVariable int id) {
        try {
            detallePedido existente = detallePedidoService.findById(id);
            detallePedidoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Detalle de pedido eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Detalle de pedido no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el detalle de pedido."));
        }
    }
}
