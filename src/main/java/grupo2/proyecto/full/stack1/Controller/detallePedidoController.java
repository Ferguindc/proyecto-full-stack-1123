package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.detallePedido;
import grupo2.proyecto.full.stack1.Service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/detalle-pedido")
public class detallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;


    @GetMapping
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
    public ResponseEntity<?> actualizarDetalle(@PathVariable int id, @RequestBody detallePedido detalleActualizado) {
        try {
            detallePedido existente = detallePedidoService.findById(id);

            // Actualizamos todos los campos del detallePedido
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
