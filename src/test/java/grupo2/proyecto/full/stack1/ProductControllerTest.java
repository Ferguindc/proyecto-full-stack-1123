package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.ProductController;
import grupo2.proyecto.full.stack1.Modelo.Product;
import grupo2.proyecto.full.stack1.Service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product p;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        p = new Product();
        p.setId(1);
        p.setName("Producto Test");
        p.setDescription("Descripción de prueba");
        p.setPrice(100.0);
    }

    @Test
    void GetAllProductosTest() {
        when(productService.findAll()).thenReturn(List.of(p));

        ResponseEntity<?> response = productController.listarProductos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllProductosSinProductosTest() {
        when(productService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = productController.listarProductos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetProductoByIdTest() {
        when(productService.findById(1)).thenReturn(p);

        ResponseEntity<?> response = productController.obtenerProducto(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetProductoByIdSinProductoTest() {
        when(productService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = productController.obtenerProducto(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostProductoTest() {
        when(productService.save(p)).thenReturn(p);

        ResponseEntity<?> response = productController.crearProducto(p);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostProductoSintaxisInvalidaTest() {
        when(productService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = productController.crearProducto(new Product());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutProductoTest() {
        when(productService.findById(1)).thenReturn(p);
        when(productService.save(any())).thenReturn(p);

        ResponseEntity<?> response = productController.actualizarProducto(1, p);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutProductoSinProductoTest() {
        when(productService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = productController.actualizarProducto(1, p);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteProductoTest() {
        when(productService.findById(1)).thenReturn(p);
        doNothing().when(productService).delete(1);

        ResponseEntity<?> response = productController.eliminarProducto(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteProductoSinProductoTest() {
        doThrow(NoSuchElementException.class).when(productService).delete(1);

        ResponseEntity<?> response = productController.eliminarProducto(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}