package grupo2.proyecto.full.stack1;
import grupo2.proyecto.full.stack1.Controller.SucursalController;
import grupo2.proyecto.full.stack1.Modelo.Sucursal;
import grupo2.proyecto.full.stack1.Service.SucursalService;

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
class SucursalControllerTest {

    @InjectMocks
    private SucursalController sucursalController;

    @Mock
    private SucursalService sucursalService;

    private Sucursal s;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        s = new Sucursal();
        s.setId(1);
        s.setNombreSucursal("Sucursal Central");
        s.setDireccionSucursal("Av. Principal 123");
    }

    @Test
    void GetAllSucursalesTest() {
        when(sucursalService.findAll()).thenReturn(List.of(s));

        ResponseEntity<?> response = sucursalController.listarSucursal();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllSucursalesSinDatosTest() {
        when(sucursalService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = sucursalController.listarSucursal();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostSucursalTest() {
        when(sucursalService.save(any())).thenReturn(s);

        ResponseEntity<?> response = sucursalController.guardarSucursal(s);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void GetSucursalByIdTest() {
        when(sucursalService.findById(1)).thenReturn(s);

        ResponseEntity<?> response = sucursalController.buscarSucursalPorId(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetSucursalByIdNoExisteTest() {
        when(sucursalService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = sucursalController.buscarSucursalPorId(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PutSucursalTest() {
        when(sucursalService.findById(1)).thenReturn(s);
        when(sucursalService.save(any())).thenReturn(s);

        ResponseEntity<?> response = sucursalController.actualizarSucursal(1, s);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutSucursalNoExisteTest() {
        when(sucursalService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = sucursalController.actualizarSucursal(1, s);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteSucursalTest() {
        doNothing().when(sucursalService).delete(1);

        ResponseEntity<?> response = sucursalController.eliminarSucursal(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteSucursalNoExisteTest() {
        doThrow(NoSuchElementException.class).when(sucursalService).delete(1);

        ResponseEntity<?> response = sucursalController.eliminarSucursal(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}