package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String getAllProducts() {
        String output="";
        for (Product product : productRepository.findAll()) {
            output+="ID Product: "+product.getId()+"\n";
            output+="Name: "+product.getName()+"\n";
            output+="Description: "+product.getDescription()+"\n";
            output+="Price: "+product.getPrice()+"\n";
            output+="Stock: "+product.getStock()+"\n";
        }
        if(output.isEmpty()){
            return "No Products Found";
        }else{
            return output;
        }
    }
    public String getProductById(int id) {
        String output="";
        if(productRepository.existsById(id)){
            Product product = productRepository.findById(id).get();
            output+="ID Product: "+product.getId()+"\n";
            output+="Name: "+product.getName()+"\n";
            output+="Description: "+product.getDescription()+"\n";
            output+="Price: "+product.getPrice()+"\n";
            output+="Stock: "+product.getStock()+"\n";
            return output;
        }else{
            return "No Product Found";
        }
    }
    public String addProduct(Product product) {
        productRepository.save(product);
        return "Product Added";
    }

    public String deleteProduct(int id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return "Product Deleted";
        }else{
            return "No Product Found";
        }
    }

    public String updateProduct(int id,Product product) {
        if(productRepository.existsById(id)){
            Product buscado = productRepository.findById(id).get();
            buscado.setName(product.getName());
            buscado.setDescription(product.getDescription());
            buscado.setPrice(product.getPrice());
            buscado.setStock(product.getStock());
            productRepository.save(buscado);
            return "Product Updated";
        }else{
            return "No Product Found";
        }
    }
}
