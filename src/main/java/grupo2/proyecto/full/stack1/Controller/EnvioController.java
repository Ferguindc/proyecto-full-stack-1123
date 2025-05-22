package grupo2.proyecto.full.stack1.Controller;


import grupo2.proyecto.full.stack1.Modelo.envio;
import grupo2.proyecto.full.stack1.Service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/envio"})
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<envio>> listarEnvio(){
      List<envio> envios = envioService.findAll();
      if(envios.isEmpty()){
          return ResponseEntity.noContent().build();
      }return ResponseEntity.ok(envios);
    }

    @PostMapping
    public ResponseEntity<envio> guardarEnvio(@RequestBody envio envio){
        envio envioNuevo= envioService.save(envio);
        return new ResponseEntity<>(envioNuevo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<envio> obtenerEnvio(@PathVariable int id){
        try{
            envio envio = envioService.findById(id);
            return ResponseEntity.ok(envio);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<envio> actualizarEnvio(@PathVariable int id, @RequestBody envio envio){
        try{
            envio buscado = envioService.findById(id);
            buscado.setNumeroEnvio(envio.getNumeroEnvio());
            buscado.setDireccionEnvio(envio.getDireccionEnvio());
            buscado.setCodigoEnvio(envio.getCodigoEnvio());
            envioService.save(buscado);
            return ResponseEntity.ok(buscado);


        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<envio> eliminarEnvio(@PathVariable int id){
        try{
            envioService.delete(id);
            return ResponseEntity.ok().build();

    } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
    }
}
