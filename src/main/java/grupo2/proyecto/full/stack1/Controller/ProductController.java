package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id) {

        return productService.getProductById(id);
    }

    @PostMapping
    public String addProduct(@RequestBody Product product) {

        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {

        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}
