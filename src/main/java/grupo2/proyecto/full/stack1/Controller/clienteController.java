package grupo2.proyecto.full.stack1.Controller;


import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class clienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String getAllClientes() {
    return clienteService.getAllClientes();
    }
    @GetMapping("/{id}")
    public String getClienteById(@PathVariable int id) {

        return clienteService.getClienteById(id);
    }

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente) {
        return clienteService.addCliente(cliente);
    }
    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable int id) {
        return clienteService.deleteCliente(id);

    }
    @PutMapping("/{id}")
    public String updateCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        return clienteService.updateCliente(id, cliente);
    }
}

