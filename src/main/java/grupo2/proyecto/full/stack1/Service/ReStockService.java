package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.reStock;
import grupo2.proyecto.full.stack1.Repository.reStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReStockService {
    @Autowired
    private reStockRepository reStockRepository;

    public List<reStock> findAll() {
        return reStockRepository.findAll();
    }
    public reStock findById(int id) {
        return reStockRepository.findById(id).get();

    }
    public reStock save(reStock reStock) {
        return reStockRepository.save(reStock);
    }
    public void delete(int id){
        reStockRepository.deleteById(id);
    }
}
