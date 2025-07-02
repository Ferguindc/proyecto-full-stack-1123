package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.DescuentoController;
import grupo2.proyecto.full.stack1.Modelo.descuento;
import grupo2.proyecto.full.stack1.Service.DescuentoService;
import grupo2.proyecto.full.stack1.Assembler.DescuentoModelAssembler;

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
class DescuentoControllerTest {

    @InjectMocks
    private DescuentoController descuentoController;

    @Mock
    private DescuentoService descuentoService;

    @Mock
    private DescuentoModelAssembler assembler;

    private descuento d;

    @BeforeEach
    void setup() {
        d = new descuento();
        d.setId(1);
    }

    // --- GET ALL ---
    @Test
    void listarDescuentos_OK() {
        when(descuentoService.findAll()).thenReturn(List.of(d));
        when(assembler.toModel(d)).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.listarDescuentos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void listarDescuentos_EmptyList() {
        when(descuentoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = descuentoController.listarDescuentos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarDescuentos_NullHandled() {
        when(descuentoService.findAll()).thenReturn(null);

        ResponseEntity<?> response = descuentoController.listarDescuentos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarDescuentos_MultipleResults() {
        when(descuentoService.findAll()).thenReturn(List.of(d, new descuento()));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.listarDescuentos();
        assertEquals(200, response.getStatusCodeValue());
    }

    // --- GET BY ID ---
    @Test
    void obtenerDescuento_OK() {
        when(descuentoService.findById(1)).thenReturn(d);
        when(assembler.toModel(d)).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.obtenerDescuento(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void obtenerDescuento_NotFound() {
        when(descuentoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = descuentoController.obtenerDescuento(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void obtenerDescuento_NullAssemblerSafe() {
        when(descuentoService.findById(1)).thenReturn(d);
        when(assembler.toModel(d)).thenReturn(null);

        ResponseEntity<?> response = descuentoController.obtenerDescuento(1);
        assertNotNull(response.getBody());
    }

    @Test
    void obtenerDescuento_InvalidId() {
        ResponseEntity<?> response = descuentoController.obtenerDescuento(-1);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    // --- POST ---
    @Test
    void crearDescuento_OK() {
        when(descuentoService.save(d)).thenReturn(d);
        when(assembler.toModel(d)).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.crearDescuento(d);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void crearDescuento_BadRequest() {
        when(descuentoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = descuentoController.crearDescuento(new descuento());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void crearDescuento_NullInput() {
        ResponseEntity<?> response = descuentoController.crearDescuento(null);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void crearDescuento_ReturnsModel() {
        when(descuentoService.save(any())).thenReturn(d);
        EntityModel<descuento> model = EntityModel.of(d);
        when(assembler.toModel(any())).thenReturn(model);

        ResponseEntity<?> response = descuentoController.crearDescuento(d);
        assertSame(model, response.getBody());
    }

    // --- PUT ---
    @Test
    void actualizarDescuento_OK() {
        when(descuentoService.findById(1)).thenReturn(d);
        when(descuentoService.save(any())).thenReturn(d);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.actualizarDescuento(1, d);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void actualizarDescuento_NotFound() {
        when(descuentoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = descuentoController.actualizarDescuento(1, d);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void actualizarDescuento_Exception() {
        when(descuentoService.findById(1)).thenReturn(d);
        when(descuentoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = descuentoController.actualizarDescuento(1, d);
        assertEquals(400, response.getStatusCodeValue());
    }

    // --- DELETE ---
    @Test
    void eliminarDescuento_OK() {
        when(descuentoService.findById(1)).thenReturn(d);
        doNothing().when(descuentoService).delete(1);

        ResponseEntity<?> response = descuentoController.eliminarDescuento(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void eliminarDescuento_NotFound() {
        doThrow(NoSuchElementException.class).when(descuentoService).delete(1);

        ResponseEntity<?> response = descuentoController.eliminarDescuento(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void eliminarDescuento_Exception() {
        doThrow(RuntimeException.class).when(descuentoService).delete(1);

        ResponseEntity<?> response = descuentoController.eliminarDescuento(1);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void eliminarDescuento_VerifyCall() {
        when(descuentoService.findById(1)).thenReturn(d);
        doNothing().when(descuentoService).delete(1);

        descuentoController.eliminarDescuento(1);
        verify(descuentoService).delete(1);
    }
}
