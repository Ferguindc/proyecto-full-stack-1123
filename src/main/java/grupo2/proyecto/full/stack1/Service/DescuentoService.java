package grupo2.proyecto.full.stack1.Service;


import grupo2.proyecto.full.stack1.Modelo.descuento;
import grupo2.proyecto.full.stack1.Repository.descuentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class DescuentoService {
    @Autowired
    private descuentoRepository descuentoRepository;

    public List<descuento> findAll() {
        return descuentoRepository.findAll();

    }
    public descuento findById(int id){
        return descuentoRepository.findById(id).get();
    }
    public descuento save(descuento descuento) {
        return descuentoRepository.save(descuento);
    }
    public void delete(int id) {
        descuentoRepository.deleteById(id);
    }

}
