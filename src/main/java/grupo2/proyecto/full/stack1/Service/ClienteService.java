package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClienteService {

    @Autowired
    private clienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(int id) {
        return clienteRepository.findById(id).get();
    }

    public Cliente save(Cliente clientes) {
        return clienteRepository.save(clientes);
    }

    public void delete(int id) {
        clienteRepository.deleteById(id);
    }
}
