package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.EnvioController;
import grupo2.proyecto.full.stack1.Modelo.envio;
import grupo2.proyecto.full.stack1.Service.EnvioService;

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
class EnvioControllerTest {

    @InjectMocks
    private EnvioController envioController;

    @Mock
    private EnvioService envioService;

    private envio envio;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        envio = new envio();
        envio.setId(1);
        envio.setNumeroEnvio(1);
        envio.setDireccionEnvio("Calle Falsa 123");
        envio.setCodigoEnvio(3455);
    }

    // --- GET ALL ---
    @Test
    void GetAllEnviosTest() {
        when(envioService.findAll()).thenReturn(List.of(envio));

        ResponseEntity<?> response = envioController.listarEnvio();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllEnviosSinEnviosTest() {
        when(envioService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = envioController.listarEnvio();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetEnvioByIdTest() {
        when(envioService.findById(1)).thenReturn(envio);

        ResponseEntity<?> response = envioController.obtenerEnvio(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetEnvioByIdSinEnvioTest() {
        when(envioService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = envioController.obtenerEnvio(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostEnvioTest() {
        when(envioService.save(envio)).thenReturn(envio);

        ResponseEntity<?> response = envioController.guardarEnvio(envio);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostEnvioDatosInvalidosTest() {
        when(envioService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = envioController.guardarEnvio(new envio());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutEnvioTest() {
        when(envioService.findById(1)).thenReturn(envio);
        when(envioService.save(any())).thenReturn(envio);

        ResponseEntity<?> response = envioController.actualizarEnvio(1, envio);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutEnvioSinEnvioTest() {
        when(envioService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = envioController.actualizarEnvio(1, envio);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteEnvioTest() {
        doNothing().when(envioService).delete(1);

        ResponseEntity<?> response = envioController.eliminarEnvio(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteEnvioSinEnvioTest() {
        doThrow(NoSuchElementException.class).when(envioService).delete(1);

        ResponseEntity<?> response = envioController.eliminarEnvio(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}