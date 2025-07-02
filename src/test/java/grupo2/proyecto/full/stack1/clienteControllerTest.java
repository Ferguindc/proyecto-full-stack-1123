package grupo2.proyecto.full.stack1;

import org.junit.jupiter.api.Test;

import grupo2.proyecto.full.stack1.Controller.clienteController;
import grupo2.proyecto.full.stack1.Modelo.Cliente;
import grupo2.proyecto.full.stack1.Service.ClienteService;
import grupo2.proyecto.full.stack1.Assembler.ClienteModelAssembler;

import org.junit.jupiter.api.BeforeEach;
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
class clienteControllerTest {

    @InjectMocks
    private clienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteModelAssembler assembler;

    private Cliente cliente;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setEmail("juan.perez@example.com");
        cliente.setRol("Cliente");
    }

    @Test
    void GetAllClientesTest() {
        when(clienteService.findAll()).thenReturn(List.of(cliente));
        when(assembler.toModel(cliente)).thenReturn(EntityModel.of(cliente));

        ResponseEntity<?> response = clienteController.listarClientes();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllClientesSinClientesTest() {
        when(clienteService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = clienteController.listarClientes();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetClienteByIdTest() {
        when(clienteService.findById(1)).thenReturn(cliente);
        when(assembler.toModel(cliente)).thenReturn(EntityModel.of(cliente));

        ResponseEntity<?> response = clienteController.obtenerCliente(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetClienteByIdSinClienteTest() {
        when(clienteService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = clienteController.obtenerCliente(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostClienteTest() {
        when(clienteService.save(cliente)).thenReturn(cliente);
        when(assembler.toModel(cliente)).thenReturn(EntityModel.of(cliente));

        ResponseEntity<?> response = clienteController.crearCliente(cliente);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostClienteDatosInvalidosTest() {
        when(clienteService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = clienteController.crearCliente(new Cliente());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutClienteTest() {
        when(clienteService.findById(1)).thenReturn(cliente);
        when(clienteService.save(any())).thenReturn(cliente);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cliente));

        ResponseEntity<?> response = clienteController.actualizarCliente(1, cliente);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutClienteSinClienteTest() {
        when(clienteService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = clienteController.actualizarCliente(1, cliente);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteClienteTest() {
        when(clienteService.findById(1)).thenReturn(cliente);
        doNothing().when(clienteService).delete(1);

        ResponseEntity<?> response = clienteController.eliminarCliente(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteClienteSinClienteTest() {
        doThrow(NoSuchElementException.class).when(clienteService).delete(1);

        ResponseEntity<?> response = clienteController.eliminarCliente(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}