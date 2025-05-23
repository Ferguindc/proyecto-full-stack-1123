package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Cargo;
import grupo2.proyecto.full.stack1.Service.cargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cargo")
public class CargoController {

    @Autowired
    private cargoService cargoService;

    @GetMapping
    public ResponseEntity<?> listarCargos() {
        List<Cargo> cargos = cargoService.findAll();
        if (cargos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay cargos registrados."));
        }
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCargo(@PathVariable int id) {
        try {
            Cargo cargo = cargoService.findById(id);
            return ResponseEntity.ok(cargo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cargo no encontrado con ID: " + id));
        }
    }


    @PostMapping
    public ResponseEntity<?> crearCargo(@RequestBody Cargo nuevoCargo) {
        try {
            Cargo guardado = cargoService.save(nuevoCargo);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el cargo."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCargo(@PathVariable int id, @RequestBody Cargo cargoActualizado) {
        try {
            Cargo existente = cargoService.findById(id);

            existente.setNombre(cargoActualizado.getNombre());
            existente.setSalario(cargoActualizado.getSalario());

            Cargo actualizado = cargoService.save(existente);
            return ResponseEntity.ok(actualizado);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cargo no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el cargo."));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCargo(@PathVariable int id) {
        try {
            Cargo existente = cargoService.findById(id);
            cargoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Cargo eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Cargo no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el cargo."));
        }
    }
}
