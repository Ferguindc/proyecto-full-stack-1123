package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Roles;
import grupo2.proyecto.full.stack1.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public ResponseEntity<?> listarRoles() {
        List<Roles> roles = rolesService.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay roles registrados."));
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRol(@PathVariable int id) {
        try {
            Roles rol = rolesService.findById(id);
            return ResponseEntity.ok(rol);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Rol no encontrado con ID: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody Roles nuevoRol) {
        try {
            Roles rolGuardado = rolesService.save(nuevoRol);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(rolGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el rol."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable int id, @RequestBody Roles rolActualizado) {
        try {
            Roles rolExistente = rolesService.findById(id);
            rolExistente.setRole(rolActualizado.getRole());
            rolExistente.setClientes(rolActualizado.getClientes());

            Roles rolGuardado = rolesService.save(rolExistente);
            return ResponseEntity.ok(rolGuardado);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Rol no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el rol."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable int id) {
        try {
            rolesService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Rol eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el rol con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el rol."));
        }
    }
}
