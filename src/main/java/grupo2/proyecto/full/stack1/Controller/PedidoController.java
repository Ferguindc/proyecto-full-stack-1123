package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.pedido;
import grupo2.proyecto.full.stack1.Service.PedidoService;
import grupo2.proyecto.full.stack1.Assembler.PedidoModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/pedido")
@Tag(name = "Pedidos", description = "Gestión de pedidos de clientes")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar pedidos",
            description = "Obtiene todos los pedidos registrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
                    @ApiResponse(responseCode = "404", description = "No hay pedidos registrados", content = @Content)
            }
    )
    public ResponseEntity<?> listarPedidos() {
        List<pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay pedidos registrados."));
        }

        List<EntityModel<pedido>> pedidosConLinks = pedidos.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(pedidosConLinks,
                linkTo(methodOn(PedidoController.class).listarPedidos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener pedido por ID",
            description = "Obtiene los detalles de un pedido mediante su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
            }
    )
    public ResponseEntity<?> obtenerPedido(@PathVariable int id) {
        try {
            pedido p = pedidoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(p));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el pedido con ID: " + id));
        }
    }

    @PostMapping
    @Operation(
            summary = "Crear pedido",
            description = "Registra un nuevo pedido",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido creado"),
                    @ApiResponse(responseCode = "400", description = "Error al crear", content = @Content)
            }
    )
    public ResponseEntity<?> crearPedido(@RequestBody pedido nuevoPedido) {
        try {
            pedido pedidoGuardado = pedidoService.save(nuevoPedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(pedidoGuardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el pedido."));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar pedido",
            description = "Actualiza los datos de un pedido existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido actualizado"),
                    @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en la actualización", content = @Content)
            }
    )
    public ResponseEntity<?> actualizarPedido(@PathVariable int id, @RequestBody pedido pedidoActualizado) {
        try {
            pedido pedidoExistente = pedidoService.findById(id);
            pedidoExistente.setPrecioPedido(pedidoActualizado.getPrecioPedido());
            pedidoExistente.setMetodoPago(pedidoActualizado.getMetodoPago());
            pedidoExistente.setCostoEnvio(pedidoActualizado.getCostoEnvio());
            pedidoExistente.setFechaEnvio(pedidoActualizado.getFechaEnvio());
            pedidoExistente.setNumSerie(pedidoActualizado.getNumSerie());
            pedidoExistente.setMetodoEnvio(pedidoActualizado.getMetodoEnvio());
            pedidoExistente.setDescuento(pedidoActualizado.getDescuento());
            pedidoExistente.setEnvio(pedidoActualizado.getEnvio());
            pedidoExistente.setSucursal(pedidoActualizado.getSucursal());

            pedido pedidoGuardado = pedidoService.save(pedidoExistente);
            return ResponseEntity.ok(assembler.toModel(pedidoGuardado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Pedido no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el pedido."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar pedido",
            description = "Elimina un pedido mediante su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido eliminado"),
                    @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error al eliminar", content = @Content)
            }
    )
    public ResponseEntity<?> eliminarPedido(@PathVariable int id) {
        try {
            pedido pedidoExistente = pedidoService.findById(id);
            pedidoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Pedido eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el pedido con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el pedido."));
        }
    }
}
