package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.CarritoController;
import grupo2.proyecto.full.stack1.Modelo.Carrito;
import grupo2.proyecto.full.stack1.Service.carritoService;
import grupo2.proyecto.full.stack1.Assembler.CarritoModelAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CarritoControllerTest {

    @InjectMocks
    private CarritoController carritoController;

    @Mock
    private carritoService carritoService;

    @Mock
    private CarritoModelAssembler assembler;

    private Carrito carrito;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        carrito = new Carrito();
        carrito.setId(1);
    }

    @Test
    void GetAllCarritosTest() {
        when(carritoService.listarCarritos()).thenReturn(List.of(carrito));
        when(assembler.toModel(carrito)).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.listarCarritos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllCarritosSinCarritosTest() {
        when(carritoService.listarCarritos()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = carritoController.listarCarritos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetCarritoByIdTest() {
        when(carritoService.findById(1)).thenReturn(carrito);
        when(assembler.toModel(carrito)).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.obtenerCarrito(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetCarritoSinCarritosTest() {
        when(carritoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = carritoController.obtenerCarrito(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostCarritoTest() {
        when(carritoService.save(carrito)).thenReturn(carrito);
        when(assembler.toModel(carrito)).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.crearCarrito(carrito);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostCarritoSintaxisInvalidaTest() {
        when(carritoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = carritoController.crearCarrito(new Carrito());
        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    void PutCarritoTest() {
        when(carritoService.findById(1)).thenReturn(carrito);
        when(carritoService.save(any())).thenReturn(carrito);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.actualizarCarrito(1, carrito);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutCarritoSinCarritosTest() {
        when(carritoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = carritoController.actualizarCarrito(1, carrito);
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void DeleteCarritoTest() {
        when(carritoService.findById(1)).thenReturn(carrito);
        doNothing().when(carritoService).delete(1);

        ResponseEntity<?> response = carritoController.eliminarCarrito(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteCarritoSinCarritosTest() {
        when(carritoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = carritoController.eliminarCarrito(1);
        assertEquals(404, response.getStatusCodeValue());
    }

}
