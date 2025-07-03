package grupo2.proyecto.full.stack1;
import grupo2.proyecto.full.stack1.Controller.PedidoController;
import grupo2.proyecto.full.stack1.Modelo.pedido;
import grupo2.proyecto.full.stack1.Service.PedidoService;

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
class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    private pedido p;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        p = new pedido();
        p.setId(1);
        p.setPrecioPedido(10000);
        p.setMetodoPago("Tarjeta");
    }

    @Test
    void GetAllPedidosTest() {
        when(pedidoService.findAll()).thenReturn(List.of(p));

        ResponseEntity<?> response = pedidoController.listarPedidos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllPedidosSinDatosTest() {
        when(pedidoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = pedidoController.listarPedidos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetPedidoByIdTest() {
        when(pedidoService.findById(1)).thenReturn(p);

        ResponseEntity<?> response = pedidoController.obtenerPedido(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetPedidoByIdNoEncontradoTest() {
        when(pedidoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = pedidoController.obtenerPedido(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostPedidoTest() {
        when(pedidoService.save(p)).thenReturn(p);

        ResponseEntity<?> response = pedidoController.crearPedido(p);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostPedidoErrorTest() {
        when(pedidoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = pedidoController.crearPedido(new pedido());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutPedidoTest() {
        when(pedidoService.findById(1)).thenReturn(p);
        when(pedidoService.save(any())).thenReturn(p);

        ResponseEntity<?> response = pedidoController.actualizarPedido(1, p);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutPedidoNoEncontradoTest() {
        when(pedidoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = pedidoController.actualizarPedido(1, p);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PutPedidoErrorTest() {
        when(pedidoService.findById(1)).thenReturn(p);
        when(pedidoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = pedidoController.actualizarPedido(1, p);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void DeletePedidoTest() {
        when(pedidoService.findById(1)).thenReturn(p);
        doNothing().when(pedidoService).delete(1);

        ResponseEntity<?> response = pedidoController.eliminarPedido(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeletePedidoNoEncontradoTest() {
        when(pedidoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = pedidoController.eliminarPedido(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeletePedidoErrorTest() {
        when(pedidoService.findById(1)).thenReturn(p);
        doThrow(RuntimeException.class).when(pedidoService).delete(1);

        ResponseEntity<?> response = pedidoController.eliminarPedido(1);
        assertEquals(500, response.getStatusCodeValue());
    }
}