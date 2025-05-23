package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;



@Transactional
@Service

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    public Product save(Product producto) {
        return productRepository.save(producto);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }


}
