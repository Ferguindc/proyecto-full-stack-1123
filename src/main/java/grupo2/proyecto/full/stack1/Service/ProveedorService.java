package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.proveedor;
import grupo2.proyecto.full.stack1.Repository.proveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class ProveedorService {
    @Autowired
    private proveedorRepository proveedorRepository;

    public List<proveedor> findAll() {
        return proveedorRepository.findAll();
    }
    public proveedor findById(int id) {
        return proveedorRepository.findById(id).get();
    }
    public proveedor save(proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
    public void delete(int id) {
        proveedorRepository.deleteById(id);
    }

}
