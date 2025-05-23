package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.envio;
import grupo2.proyecto.full.stack1.Service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/envio")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<?> listarEnvio() {
        List<envio> envios = envioService.findAll();
        if (envios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No hay registros de envíos."));
        }
        return ResponseEntity.ok(envios);
    }

    @PostMapping
    public ResponseEntity<?> guardarEnvio(@RequestBody envio envio) {
        try {
            envio nuevo = envioService.save(envio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo guardar el envío."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEnvio(@PathVariable int id) {
        try {
            envio encontrado = envioService.findById(id);
            return ResponseEntity.ok(encontrado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el envío con ID: " + id));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEnvio(@PathVariable int id, @RequestBody envio envio) {
        try {
            envio existente = envioService.findById(id);
            existente.setNumeroEnvio(envio.getNumeroEnvio());
            existente.setDireccionEnvio(envio.getDireccionEnvio());
            existente.setCodigoEnvio(envio.getCodigoEnvio());

            envio actualizado = envioService.save(existente);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el envío con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo actualizar el envío."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEnvio(@PathVariable int id) {
        try {
            envioService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Envío eliminado correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el envío con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el envío."));

        }
    }
}
