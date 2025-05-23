package grupo2.proyecto.full.stack1.Service;


import grupo2.proyecto.full.stack1.Modelo.Sucursal;
import grupo2.proyecto.full.stack1.Repository.sucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class SucursalService {
    @Autowired
    private sucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }
    public Sucursal findById(int id) {
        return sucursalRepository.findById(id).get();
    }
    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
    public void delete(int id) {
        sucursalRepository.deleteById(id);
    }


}

