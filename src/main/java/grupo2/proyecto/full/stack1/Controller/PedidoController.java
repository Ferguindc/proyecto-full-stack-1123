package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.pedido;
import grupo2.proyecto.full.stack1.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> listarPedidos() {
        List<pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay pedidos registrados."));
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedido(@PathVariable int id) {
        try {
            pedido p = pedidoService.findById(id);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el pedido con ID: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody pedido nuevoPedido) {
        try {
            pedido pedidoGuardado = pedidoService.save(nuevoPedido);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(pedidoGuardado);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el pedido."));
        }
    }

    @PutMapping("/{id}")
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
            return ResponseEntity.ok(pedidoGuardado);

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Pedido no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el pedido."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable int id) {
        try {
            pedido pedidoExistente = pedidoService.findById(id);
            pedidoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Pedido eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el pedido con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el pedido."));
        }
    }
}
