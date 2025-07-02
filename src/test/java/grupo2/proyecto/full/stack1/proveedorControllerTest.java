package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.proveedorController;
import grupo2.proyecto.full.stack1.Modelo.proveedor;
import grupo2.proyecto.full.stack1.Repository.proveedorRepository;
import grupo2.proyecto.full.stack1.Service.ProveedorService;

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
class proveedorControllerTest {

        @InjectMocks
        private proveedorController proveedorController;

        @Mock
        private proveedorRepository proveedorRepository;

        @Mock
        private ProveedorService proveedorService;

        private proveedor p;

        // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
        @BeforeEach
        void setup() {
            p = new proveedor();
            p.setId(1);
            p.setNombreProveedor("Proveedor Test");
            p.setDireccionProveedor("Calle Falsa 123");
            p.setTelefonoProveedor("123456789");
            p.setEmailProveedor("test@proveedor.com");
        }

        @Test
        void GetAllProveedorTest() {
            when(proveedorRepository.findAll()).thenReturn(List.of(p));

            ResponseEntity<?> response = proveedorController.listarProveedor();
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void GetAllProveedorSinDatosTest() {
            when(proveedorRepository.findAll()).thenReturn(Collections.emptyList());

            ResponseEntity<?> response = proveedorController.listarProveedor();
            assertEquals(204, response.getStatusCodeValue());
        }

        @Test
        void PostProveedorTest() {
            when(proveedorRepository.save(p)).thenReturn(p);

            ResponseEntity<?> response = proveedorController.crearProveedor(p);
            assertEquals(201, response.getStatusCodeValue());
        }

        @Test
        void PostProveedorErrorTest() {
            when(proveedorRepository.save(any())).thenThrow(RuntimeException.class);

            ResponseEntity<?> response = proveedorController.crearProveedor(new proveedor());
            assertEquals(400, response.getStatusCodeValue());
        }

        @Test
        void GetProveedorByIdTest() {
            when(proveedorService.findById(1)).thenReturn(p);

            ResponseEntity<?> response = proveedorController.buscarProveedor(1);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void GetProveedorByIdNoExisteTest() {
            when(proveedorService.findById(99)).thenThrow(NoSuchElementException.class);

            ResponseEntity<?> response = proveedorController.buscarProveedor(99);
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void PutProveedorTest() {
            when(proveedorService.findById(1)).thenReturn(p);
            when(proveedorRepository.save(any())).thenReturn(p);

            ResponseEntity<?> response = proveedorController.actualizarProveedor(1, p);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void PutProveedorNoExisteTest() {
            when(proveedorService.findById(1)).thenThrow(NoSuchElementException.class);

            ResponseEntity<?> response = proveedorController.actualizarProveedor(1, p);
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void DeleteProveedorTest() {
            doNothing().when(proveedorService).delete(1);

            ResponseEntity<?> response = proveedorController.eliminarProveedor(1);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void DeleteProveedorNoExisteTest() {
            doThrow(NoSuchElementException.class).when(proveedorService).delete(1);

            ResponseEntity<?> response = proveedorController.eliminarProveedor(1);
            assertEquals(404, response.getStatusCodeValue());
        }
}