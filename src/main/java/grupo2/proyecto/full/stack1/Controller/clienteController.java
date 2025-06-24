package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes del sistema")
public class clienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Retorna una lista con todos los clientes registrados en el sistema.")
    public ResponseEntity<?> listarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay clientes registrados."));
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Retorna la información de un cliente específico usando su ID.")
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
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema con los datos proporcionados.")
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
    @Operation(summary = "Actualizar un cliente", description = "Actualiza la información de un cliente existente con el ID proporcionado.")
    public ResponseEntity<?> actualizarCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente existente = clienteService.findById(id);
            existente.setNombre(clienteActualizado.getNombre());
            existente.setApellido(clienteActualizado.getApellido());
            existente.setEmail(clienteActualizado.getEmail());
            existente.setRol(clienteActualizado.getRol());

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
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente existente del sistema utilizando su ID.")
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
