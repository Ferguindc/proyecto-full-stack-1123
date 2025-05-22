package grupo2.proyecto.full.stack1.Controller;


import grupo2.proyecto.full.stack1.Modelo.Sucursal;
import grupo2.proyecto.full.stack1.Service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/sucursal"})
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> listarSucursal() {
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();

        }return ResponseEntity.ok(sucursales);
    }
    @PostMapping
    public ResponseEntity<Sucursal> guardarSucursal(@RequestBody Sucursal sucursal) {

        Sucursal sucursalNuevo = sucursalService.save(sucursal);
        return new ResponseEntity<>(sucursalNuevo, HttpStatus.CREATED);


    }
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscarSucursalPorId(@PathVariable int id) {
        try{
            Sucursal sucursal = sucursalService.findById(id);
            return ResponseEntity.ok(sucursal);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable int id, @RequestBody Sucursal sucursal) {
        try{
            Sucursal buscado = sucursalService.findById(id);
            buscado.setId(id);
            buscado.setDireccionSucursal(sucursal.getDireccionSucursal());
            buscado.setNombreSucursal(sucursal.getNombreSucursal());
            sucursalService.save(buscado);
            return ResponseEntity.ok(buscado);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sucursal> eliminarSucursal(@PathVariable int id) {
        try{
            sucursalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
