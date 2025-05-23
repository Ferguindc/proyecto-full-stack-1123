package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Cargo;
import grupo2.proyecto.full.stack1.Repository.cargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cargoService {

    @Autowired
    private cargoRepository cargoRepository;

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    public Cargo findById(int id) {
        return cargoRepository.findById(id).get();
    }

    public Cargo save(Cargo cargos) {
        return cargoRepository.save(cargos);
    }

    public void delete(int id) {
        cargoRepository.deleteById(id);
    }
}
