package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.envio;
import grupo2.proyecto.full.stack1.Repository.envioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class EnvioService {
    @Autowired
    private envioRepository envioRepository;

    public List<envio> findAll() {
        return envioRepository.findAll();

    }
    public envio findById(int id) {
        return envioRepository.findById(id).get();
    }
    public envio save(envio envio) {
        return envioRepository.save(envio);
    }
    public void delete(int id) {
        envioRepository.deleteById(id);
    }
}
