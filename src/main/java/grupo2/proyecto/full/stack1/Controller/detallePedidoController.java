package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.detallePedido;
import grupo2.proyecto.full.stack1.Service.DetallePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/detalle-pedido")
@Tag(name = "Detalles de Pedido", description = "Gestión de detalles asociados a pedidos de productos")
public class detallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de pedidos", description = "Obtiene una lista con todos los detalles de pedidos registrados.")
    public ResponseEntity<?> listarDetalles() {
        List<detallePedido> detalles = detallePedidoService.findAll();
        if (detalles.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay detalles de pedidos registrados."));
        }
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de pedido por ID", description = "Obtiene un detalle de pedido específico a partir de su ID.")
    public ResponseEntity<?> obtenerDetalle(@PathVariable int id) {
        try {
            detallePedido detalle = detallePedidoService.findById(id);
            return ResponseEntity.ok(detalle);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Detalle de pedido no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de pedido", description = "Registra un nuevo detalle para un pedido.")
    public ResponseEntity<?> crearDetalle(@RequestBody detallePedido nuevoDetalle) {
        try {
            detallePedido guardado = detallePedidoService.save(nuevoDetalle);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(guardado);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el detalle de pedido."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un detalle de pedido", description = "Modifica un detalle de pedido existente según su ID.")
    public ResponseEntity<?> actualizarDetalle(@PathVariable int id, @RequestBody detallePedido detalleActualizado) {
        try {
            detallePedido existente = detallePedidoService.findById(id);
            existente.setCantidadProductos(detalleActualizado.getCantidadProductos());
            existente.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
            existente.setProduct(detalleActualizado.getProduct());
            existente.setPedido(detalleActualizado.getPedido());

            detallePedido actualizado = detallePedidoService.save(existente);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Detalle de pedido no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el detalle de pedido."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle de pedido", description = "Elimina un detalle de pedido utilizando su ID.")
    public ResponseEntity<?> eliminarDetalle(@PathVariable int id) {
        try {
            detallePedido existente = detallePedidoService.findById(id);
            detallePedidoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Detalle de pedido eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Detalle de pedido no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el detalle de pedido."));
        }
    }
}
