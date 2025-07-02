package grupo2.proyecto.full.stack1;
import grupo2.proyecto.full.stack1.Controller.ReStockController;
import grupo2.proyecto.full.stack1.Modelo.reStock;
import grupo2.proyecto.full.stack1.Service.ReStockService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ReStockControllerTest {
    @InjectMocks
    private ReStockController reStockController;

    @Mock
    private ReStockService reStockService;

    private reStock r;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        r = new reStock();
        r.setId(1);
        r.setStock(100);
        r.setFechaReStock(LocalDate.now());
    }

    @Test
    void GetAllReStockTest() {
        when(reStockService.findAll()).thenReturn(List.of(r));

        ResponseEntity<?> response = reStockController.listarRestock();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllReStockSinDatosTest() {
        when(reStockService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = reStockController.listarRestock();
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void GetReStockByIdTest() {
        when(reStockService.findById(1)).thenReturn(r);

        ResponseEntity<?> response = reStockController.buscarRestock(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetReStockByIdNoExisteTest() {
        when(reStockService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = reStockController.buscarRestock(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PutReStockTest() {
        when(reStockService.findById(1)).thenReturn(r);
        when(reStockService.save(any())).thenReturn(r);

        ResponseEntity<?> response = reStockController.actualizarRestock(1, r);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutReStockNoExisteTest() {
        when(reStockService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = reStockController.actualizarRestock(1, r);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteReStockTest() {
        doNothing().when(reStockService).delete(1);

        ResponseEntity<?> response = reStockController.eliminar(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteReStockNoExisteTest() {
        doThrow(NoSuchElementException.class).when(reStockService).delete(1);

        ResponseEntity<?> response = reStockController.eliminar(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}