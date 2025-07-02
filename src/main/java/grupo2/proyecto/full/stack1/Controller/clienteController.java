package grupo2.proyecto.full.stack1.Controller;
import grupo2.proyecto.full.stack1.Assembler.ClienteModelAssembler;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private ClienteModelAssembler assembler;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Retorna una lista con todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay clientes registrados")
    })
    public ResponseEntity<?> listarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay clientes registrados."));
        }

        List<EntityModel<Cliente>> clientesConLinks = clientes.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(clientesConLinks,
                linkTo(methodOn(clienteController.class).listarClientes()).withSelfRel()));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Retorna la información de un cliente específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<?> obtenerCliente(@PathVariable int id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(assembler.toModel(cliente));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cliente no encontrado con ID: " + id));
        }
    }


    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> crearCliente(@RequestBody Cliente nuevoCliente) {
        try {
            Cliente guardado = clienteService.save(nuevoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el cliente."));
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<?> actualizarCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente existente = clienteService.findById(id);
            existente.setNombre(clienteActualizado.getNombre());
            existente.setApellido(clienteActualizado.getApellido());
            existente.setEmail(clienteActualizado.getEmail());
            existente.setRol(clienteActualizado.getRol());
            Cliente actualizado = clienteService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cliente no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el cliente."));
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar")
    })
    public ResponseEntity<?> eliminarCliente(
            @Parameter(description = "ID del cliente", required = true) @PathVariable int id) {
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