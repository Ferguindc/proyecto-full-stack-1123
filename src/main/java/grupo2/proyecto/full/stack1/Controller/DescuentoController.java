package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.descuento;
import grupo2.proyecto.full.stack1.Service.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/descuento"})
public class DescuentoController {
    @Autowired
    private DescuentoService descuentoService;

    @GetMapping
    public ResponseEntity<List<descuento>> listarDescuentos(){
        List<descuento> descuentos = descuentoService.findAll();
       if (descuentos.isEmpty()) {
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok(descuentos);
    }
    @PostMapping ResponseEntity<descuento> guardarDescuento(@RequestBody descuento descuento){

        descuento descuentonuevo = descuentoService.save(descuento);
        return new ResponseEntity<>(descuentonuevo, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<descuento> buscarDescuento(@PathVariable int id){
        try{
            descuento descuento = descuentoService.findById(id);
            return ResponseEntity.ok(descuento);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<descuento> actualizarDescuento(@PathVariable int id, @RequestBody descuento descuento){
        try {
            descuento buscado = descuentoService.findById(id);
            buscado.setDescuento(descuento.getDescuento());
            descuentoService.save(buscado);
            return ResponseEntity.ok(buscado);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/{id}")
        public ResponseEntity<descuento> eliminarDescuento(@PathVariable int id){
            try{
                descuentoService.delete(id);
                return ResponseEntity.ok().build();

            }catch (Exception e){
                return ResponseEntity.notFound().build();
            }
        }


}
