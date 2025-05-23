package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cliente")
public class clienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> listarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay clientes registrados."));
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable int id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cliente no encontrado con ID: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente nuevoCliente) {
        try {
            Cliente guardado = clienteService.save(nuevoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el cliente."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente existente = clienteService.findById(id);

            existente.setNombre(clienteActualizado.getNombre());
            existente.setApellido(clienteActualizado.getApellido());
            existente.setEmail(clienteActualizado.getEmail());

            Cliente actualizado = clienteService.save(existente);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cliente no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el cliente."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable int id) {
        try {
            clienteService.findById(id);
            clienteService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Cliente eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cliente no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el cliente."));
        }
    }
}
