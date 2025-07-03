package grupo2.proyecto.full.stack1;

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
class detallePedidoControllerTest {

    @InjectMocks
    private detallePedidoController detallePedidoController;

    @Mock
    private DetallePedidoService detallePedidoService;

    @Mock
    private DetallePedidoModelAssembler assembler;

    private detallePedido detalle;

    @BeforeEach
    void setup() {
        detalle = new detallePedido();
        detalle.setId(1);
        detalle.setCantidadProductos(5);
        detalle.setPrecioUnitario(1000);
    }

    @Test
    void GetAllDetallesTest() {
        when(detallePedidoService.findAll()).thenReturn(List.of(detalle));
        when(assembler.toModel(detalle)).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.listarDetalles();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllDetallesSinDetallesTest() {
        when(detallePedidoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = detallePedidoController.listarDetalles();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetDetalleByIdTest() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(assembler.toModel(detalle)).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.obtenerDetalle(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetDetalleByIdSinDetalleTest() {
        when(detallePedidoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = detallePedidoController.obtenerDetalle(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostDetalleTest() {
        when(detallePedidoService.save(detalle)).thenReturn(detalle);
        when(assembler.toModel(detalle)).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.crearDetalle(detalle);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostDetalleDatosInvalidosTest() {
        when(detallePedidoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = detallePedidoController.crearDetalle(new detallePedido());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutDetalleTest() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        when(detallePedidoService.save(any())).thenReturn(detalle);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(detalle));

        ResponseEntity<?> response = detallePedidoController.actualizarDetalle(1, detalle);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutDetalleSinDetalleTest() {
        when(detallePedidoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = detallePedidoController.actualizarDetalle(1, detalle);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteDetalleTest() {
        when(detallePedidoService.findById(1)).thenReturn(detalle);
        doNothing().when(detallePedidoService).delete(1);

        ResponseEntity<?> response = detallePedidoController.eliminarDetalle(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteDetalleSinDetalleTest() {
        doThrow(NoSuchElementException.class).when(detallePedidoService).delete(1);

        ResponseEntity<?> response = detallePedidoController.eliminarDetalle(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}