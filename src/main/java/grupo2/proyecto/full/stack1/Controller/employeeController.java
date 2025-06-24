package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Employee;
import grupo2.proyecto.full.stack1.Service.employeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/empleado")
@Tag(name = "Empleados", description = "Gestión de empleados dentro del sistema")
public class employeeController {

    @Autowired
    private employeeService employeeService;

    @GetMapping
    @Operation(summary = "Listar todos los empleados", description = "Obtiene una lista con todos los empleados registrados en el sistema.")
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> empleados = employeeService.getAllEmployee();
        if (empleados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay empleados registrados."));
        }
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Obtiene la información de un empleado específico a partir de su ID.")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        try {
            Employee empleado = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(empleado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Empleado no encontrado con ID: " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo empleado", description = "Registra un nuevo empleado en el sistema.")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            Employee nuevo = employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el empleado."));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado", description = "Modifica los datos de un empleado existente según su ID.")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable int id, @RequestBody Employee empleadoActualizado) {
        try {
            Employee empleadoExistente = employeeService.getEmployeeById(id);
            empleadoExistente.setNombre(empleadoActualizado.getNombre());
            empleadoExistente.setApellido(empleadoActualizado.getApellido());
            empleadoExistente.setZipcode(empleadoActualizado.getZipcode());
            empleadoExistente.setEmail(empleadoActualizado.getEmail());
            empleadoExistente.setTelefono(empleadoActualizado.getTelefono());
            empleadoExistente.setCargo(empleadoActualizado.getCargo());
            empleadoExistente.setSucursal(empleadoActualizado.getSucursal());

            Employee empleadoGuardado = employeeService.addEmployee(empleadoExistente);
            return ResponseEntity.ok(empleadoGuardado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el empleado."));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado del sistema a partir de su ID.")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(Map.of("mensaje", "Empleado eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Empleado no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el empleado."));
        }
    }
}
