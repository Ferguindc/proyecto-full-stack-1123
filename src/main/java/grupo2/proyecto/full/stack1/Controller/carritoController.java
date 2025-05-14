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

    @PostMapping("/pagar")
    public String pagarPedidos(){
        return carritoService.pagarPedidos();
    }

    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable int id) {
        return carritoService.eliminarPedido(id);
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarPago(@PathVariable int id) {
        return carritoService.cancelarPago(id);
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarPedido(@PathVariable int id, @RequestBody Carrito carrito) {
        return carritoService.actualizarPedido(id, carrito);
    }

    @PostMapping("/cerrar/{id}")
    public String cerrar(@PathVariable int id) {
        return carritoService.cerrarCarrito(id);
    }



}
