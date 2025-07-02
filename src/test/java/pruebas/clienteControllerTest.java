package pruebas;

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

    @BeforeEach
    void setup() {
        carrito = new Carrito();
        carrito.setId(1);
    }

    // --- GET ALL ---
    @Test
    void listarCarritos_OK() {
        when(carritoService.listarCarritos()).thenReturn(List.of(carrito));
        when(assembler.toModel(carrito)).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.listarCarritos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void listarCarritos_EmptyList() {
        when(carritoService.listarCarritos()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = carritoController.listarCarritos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarCarritos_NullHandled() {
        when(carritoService.listarCarritos()).thenReturn(null);

        ResponseEntity<?> response = carritoController.listarCarritos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarCarritos_MultipleResults() {
        when(carritoService.listarCarritos()).thenReturn(List.of(carrito, new Carrito()));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.listarCarritos();
        assertEquals(200, response.getStatusCodeValue());
    }

    // --- GET BY ID ---
    @Test
    void obtenerCarrito_OK() {
        when(carritoService.findById(1)).thenReturn(carrito);
        when(assembler.toModel(carrito)).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.obtenerCarrito(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void obtenerCarrito_NotFound() {
        when(carritoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = carritoController.obtenerCarrito(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void obtenerCarrito_NullId() {
        ResponseEntity<?> response = carritoController.obtenerCarrito(0);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void obtenerCarrito_AssemblerNullSafe() {
        when(carritoService.findById(1)).thenReturn(carrito);
        when(assembler.toModel(any())).thenReturn(null);

        ResponseEntity<?> response = carritoController.obtenerCarrito(1);
        assertNotNull(response.getBody());
    }

    // --- POST ---
    @Test
    void crearCarrito_OK() {
        when(carritoService.save(carrito)).thenReturn(carrito);
        when(assembler.toModel(carrito)).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.crearCarrito(carrito);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void crearCarrito_BadRequest() {
        when(carritoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = carritoController.crearCarrito(new Carrito());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void crearCarrito_NullInput() {
        ResponseEntity<?> response = carritoController.crearCarrito(null);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void crearCarrito_ReturnsModel() {
        when(carritoService.save(any())).thenReturn(carrito);
        EntityModel<Carrito> model = EntityModel.of(carrito);
        when(assembler.toModel(any())).thenReturn(model);

        ResponseEntity<?> response = carritoController.crearCarrito(carrito);
        assertSame(model, response.getBody());
    }

    // --- PUT ---
    @Test
    void actualizarCarrito_OK() {
        when(carritoService.findById(1)).thenReturn(carrito);
        when(carritoService.save(any())).thenReturn(carrito);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(carrito));

        ResponseEntity<?> response = carritoController.actualizarCarrito(1, carrito);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void actualizarCarrito_NotFound() {
        when(carritoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = carritoController.actualizarCarrito(1, carrito);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void actualizarCarrito_ThrowsException() {
        when(carritoService.findById(1)).thenReturn(carrito);
        when(carritoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = carritoController.actualizarCarrito(1, carrito);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void actualizarCarrito_ChangesCliente() {
        Carrito actualizado = new Carrito();
        actualizado.setCliente("nuevo cliente");

        when(carritoService.findById(1)).thenReturn(carrito);
        when(carritoService.save(any())).thenReturn(actualizado);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(actualizado));

        ResponseEntity<?> response = carritoController.actualizarCarrito(1, actualizado);
        assertEquals(200, response.getStatusCodeValue());
    }

    // --- DELETE ---
    @Test
    void eliminarCarrito_OK() {
        when(carritoService.findById(1)).thenReturn(carrito);
        doNothing().when(carritoService).delete(1);

        ResponseEntity<?> response = carritoController.eliminarCarrito(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void eliminarCarrito_NotFound() {
        when(carritoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = carritoController.eliminarCarrito(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void eliminarCarrito_Exception() {
        when(carritoService.findById(1)).thenReturn(carrito);
        doThrow(new RuntimeException()).when(carritoService).delete(1);

        ResponseEntity<?> response = carritoController.eliminarCarrito(1);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void eliminarCarrito_VerifyCalled() {
        when(carritoService.findById(1)).thenReturn(carrito);
        doNothing().when(carritoService).delete(1);

        carritoController.eliminarCarrito(1);
        verify(carritoService).delete(1);
    }
}
