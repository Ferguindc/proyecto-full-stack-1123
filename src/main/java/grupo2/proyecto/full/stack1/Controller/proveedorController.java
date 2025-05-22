package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.proveedor;
import grupo2.proyecto.full.stack1.Repository.proveedorRepository;
import grupo2.proyecto.full.stack1.Service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class proveedorController {
     @Autowired
    private proveedorRepository proveedorRepository;
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<proveedor>> listarProveedor() {
         List<proveedor> proveedores = proveedorRepository.findAll();
         if (proveedores.isEmpty()) {
             return ResponseEntity.noContent().build();


         } return ResponseEntity.ok(proveedores);
     }

     @PostMapping
    public ResponseEntity<proveedor> crearProveedor(@RequestBody proveedor proveedor) {
         proveedor proveedorNuevo = proveedorRepository.save(proveedor);
         return new ResponseEntity<>(proveedorNuevo, HttpStatus.CREATED);
     }

    @GetMapping("/{id}")
    public ResponseEntity<proveedor> buscarProveedor(@PathVariable int id) {
         try{
             proveedor proveedor = proveedorService.findById(id);
             return ResponseEntity.ok(proveedor);

         } catch (Exception e) {
             return ResponseEntity.noContent().build();
         }
    }
    @PutMapping("/{id}")
    public ResponseEntity<proveedor> actualizarProveedor(@PathVariable int id, @RequestBody proveedor proveedor) {
        try{
            proveedor buscado = proveedorService.findById(id);
            buscado.setDireccionProveedor(proveedor.getDireccionProveedor());
            buscado.setNombreProveedor(proveedor.getNombreProveedor());
            buscado.setTelefonoProveedor(proveedor.getTelefonoProveedor());
            buscado.setEmailProveedor(proveedor.getEmailProveedor());
            proveedorRepository.save(buscado);
            return ResponseEntity.ok(buscado);

        } catch (Exception e) {
            return ResponseEntity.noContent().build();

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<proveedor> eliminarProveedor(@PathVariable int id) {
        try{
            proveedorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
