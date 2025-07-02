package pruebas;

import grupo2.proyecto.full.stack1.Controller.detallePedidoController;
import grupo2.proyecto.full.stack1.Modelo.detallePedido;
import grupo2.proyecto.full.stack1.Service.DetallePedidoService;
import grupo2.proyecto.full.stack1.Assembler.DetallePedidoModelAssembler;

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
class DetallePedidoControllerTest {

    @InjectMocks
    private DetallePedidoController detallePedidoController;

    @Mock
    private DetallePedidoService detallePedidoService;

    @Mock
    private DetallePedidoModelAssembler assembler;

    private detallePedido detalle;

    @BeforeEach
    void setup() {
        detalle = new detallePedido();
        detalle.setId(1);
    }

    // --- GET ALL ---
    @Test
    void listarDetalles_OK() {
        when(detallePedidoService.findAll()).thenReturn(List.of(detalle));
        when(assembler.toModel(detalle)).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.listarDetalles();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void listarDetalles_EmptyList() {
        when(detallePedidoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = detallePedidoController.listarDetalles();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarDetalles_NullHandled() {
        when(detallePedidoService.findAll()).thenReturn(null);

        ResponseEntity<?> response = detallePedidoController.listarDetalles();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void listarDetalles_MultipleResults() {
        when(detallePedidoService.findAll()).thenReturn(List.of(detalle, new detallePedido()));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.listarDetalles();
        assertEquals(200, response.getStatusCodeValue());
    }

    // --- GET BY ID ---
    @Test
    void obtenerDetalle_OK() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(assembler.toModel(detalle)).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.obtenerDetalle(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void obtenerDetalle_NotFound() {
        when(detallePedidoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = detallePedidoController.obtenerDetalle(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void obtenerDetalle_NullAssemblerSafe() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(assembler.toModel(detalle)).thenReturn(null);

        ResponseEntity<?> response = detallePedidoController.obtenerDetalle(1);
        assertNotNull(response.getBody());
    }

    @Test
    void obtenerDetalle_InvalidId() {
        ResponseEntity<?> response = detallePedidoController.obtenerDetalle(-1);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    // --- POST ---
    @Test
    void crearDetalle_OK() {
        when(detallePedidoService.save(detalle)).thenReturn(detalle);
        when(assembler.toModel(detalle)).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.crearDetalle(detalle);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void crearDetalle_BadRequest() {
        when(detallePedidoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = detallePedidoController.crearDetalle(new detallePedido());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void crearDetalle_NullInput() {
        ResponseEntity<?> response = detallePedidoController.crearDetalle(null);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void crearDetalle_ReturnsModel() {
        when(detallePedidoService.save(any())).thenReturn(detalle);
        EntityModel<detallePedido> model = EntityModel.of(detalle);
        when(assembler.toModel(any())).thenReturn(model);

        ResponseEntity<?> response = detallePedidoController.crearDetalle(detalle);
        assertSame(model, response.getBody());
    }

    // --- PUT ---
    @Test
    void actualizarDetalle_OK() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(detallePedidoService.save(any())).thenReturn(detalle);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.actualizarDetalle(1, detalle);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void actualizarDetalle_NotFound() {
        when(detallePedidoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = detallePedidoController.actualizarDetalle(1, detalle);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void actualizarDetalle_Exception() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(detallePedidoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = detallePedidoController.actualizarDetalle(1, detalle);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void actualizarDetalle_DataChanged() {
        detallePedido actualizado = new detallePedido();
        actualizado.setCantidadProductos(10);

        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(detallePedidoService.save(any())).thenReturn(actualizado);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(actualizado));

        ResponseEntity<?> response = detallePedidoController.actualizarDetalle(1, actualizado);
        assertEquals(200, response.getStatusCodeValue());
    }

    // --- DELETE ---
    @Test
    void eliminarDetalle_OK() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        doNothing().when(detallePedidoService).delete(1);

        ResponseEntity<?> response = detallePedidoController.eliminarDetalle(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void eliminarDetalle_NotFound() {
        when(detallePedidoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = detallePedidoController.eliminarDetalle(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void eliminarDetalle_Exception() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        doThrow(RuntimeException.class).when(detallePedidoService).delete(1);

        ResponseEntity<?> response = detallePedidoController.eliminarDetalle(1);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void eliminarDetalle_VerifyCall() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        doNothing().when(detallePedidoService).delete(1);

        detallePedidoController.eliminarDetalle(1);
        verify(detallePedidoService).delete(1);
    }
}
