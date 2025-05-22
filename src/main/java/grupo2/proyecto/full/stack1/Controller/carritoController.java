package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Service.carritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class carritoController {

    @Autowired
    private carritoService carritoService;

    @GetMapping
    public String getAllCarritos() {
        return carritoService.getAllCarritos();
    }

    @GetMapping("/{id}")
    public String getCargoById(@PathVariable int id) {
        return carritoService.getCarritoById(id);
    }

    @PostMapping()
    public String addCarrito(@RequestBody Carrito carrito) {
        return carritoService.addCarrito(carrito);
    }

    @DeleteMapping("/{id}")
    public String deleteCarrito(@PathVariable int id) {
        return carritoService.deleteCarrito(id);
    }

    @PutMapping("/{id}")
    public String updateCarrito(@PathVariable int id, @RequestBody Carrito carrito) {
        return carritoService.updateCarrito(id, carrito);
    }
}
