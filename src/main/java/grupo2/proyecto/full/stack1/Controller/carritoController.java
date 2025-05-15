package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Service.carritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class carritoController {

    @Autowired
    private carritoService carritoService;

    @GetMapping
    public String getAllCarritos() {
        return carritoService.getAllCarritos();
    }

    @PostMapping()
    public String addCarrito(@RequestBody Carrito carrito) {
        return carritoService.addCarrito(carrito);
    }

    @DeleteMapping("/{id}")
    public String deletePedido(@PathVariable int id) {
        return carritoService.deletePedido(id);
    }

    @PutMapping("/{id}")
    public String updatePedido(@PathVariable int id, @RequestBody Carrito carrito) {
        return carritoService.updatePedido(id, carrito);
    }
}
