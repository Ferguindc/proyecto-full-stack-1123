package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private clienteRepository clienteRepository;

    public String getAllClientes() {
        String output = "";
        for (Cliente cliente : clienteRepository.findAll()) {
            output += "Id: " + cliente.getId() + "\n";
            output += "Nombre: " + cliente.getNombre() + "\n";
            output += "Apellido: " + cliente.getApellido() + "\n";
            output += "Email: " + cliente.getEmail() + "\n";


        }
        if(output.isEmpty()) {
            return "No se encontraron clientes";

        } else {
            return output;
        }
    }
    public String getClienteById(int id) {
        String output = "";
        if (clienteRepository.existsById(id)) {
            Cliente cliente = clienteRepository.findById(id).get();
            output += "Id: " + cliente.getId() + "\n";
            output += "Nombre: " + cliente.getNombre() + "\n";
            output += "Apellido: " + cliente.getApellido() + "\n";
            output += "Email: " + cliente.getEmail() + "\n";

        }return output;
    }
    public String addCliente(Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente guardado";
    }
    public String deleteCliente(int id) {
       if (clienteRepository.existsById(id)) {
           clienteRepository.deleteById(id);
           return "Cliente eliminado";
       }else {
        return "No se encontraron cliente";
       }
    }
    public String updateCliente(int id,Cliente cliente) {
        if (clienteRepository.existsById(cliente.getId())) {
            Cliente buscado = clienteRepository.findById(cliente.getId()).get();
            buscado.setId(cliente.getId());
            buscado.setNombre(cliente.getNombre());
            buscado.setApellido(cliente.getApellido());
            buscado.setEmail(cliente.getEmail());
            clienteRepository.save(buscado);
            return "Cliente actualizado";

        }return "No se encontraron cliente";

    }
}
