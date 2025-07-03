package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.inventoryController;
import grupo2.proyecto.full.stack1.Modelo.Inventory;
import grupo2.proyecto.full.stack1.Service.InventoryService;

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
class inventoryControllerTest {


    @InjectMocks
    private inventoryController inventoryController;

    @Mock
    private InventoryService inventoryService;

    private Inventory inventory;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        inventory = new Inventory();
        inventory.setId(1);
        inventory.setStock(100);
        // Puedes agregar más propiedades si tu clase Inventory las tiene
    }

    @Test
    void GetAllInventarioTest() {
        when(inventoryService.getAllInventory()).thenReturn(List.of(inventory));

        ResponseEntity<?> response = inventoryController.listarInventario();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllInventarioSinDatosTest() {
        when(inventoryService.getAllInventory()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = inventoryController.listarInventario();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetAllInventarioErrorInternoTest() {
        when(inventoryService.getAllInventory()).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = inventoryController.listarInventario();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void GetInventarioByIdTest() {
        when(inventoryService.getInventoryId(1)).thenReturn(inventory);

        ResponseEntity<?> response = inventoryController.obtenerInventario(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetInventarioByIdNoEncontradoTest() {
        when(inventoryService.getInventoryId(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = inventoryController.obtenerInventario(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostInventarioTest() {
        when(inventoryService.addProduct(inventory)).thenReturn(inventory);

        ResponseEntity<?> response = inventoryController.agregarInventario(inventory);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostInventarioErrorTest() {
        when(inventoryService.addProduct(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = inventoryController.agregarInventario(new Inventory());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutInventarioTest() {
        when(inventoryService.getInventoryId(1)).thenReturn(inventory);
        when(inventoryService.addProduct(any())).thenReturn(inventory);

        ResponseEntity<?> response = inventoryController.actualizarInventario(1, inventory);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutInventarioNoEncontradoTest() {
        when(inventoryService.getInventoryId(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = inventoryController.actualizarInventario(1, inventory);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PutInventarioErrorTest() {
        when(inventoryService.getInventoryId(1)).thenReturn(inventory);
        when(inventoryService.addProduct(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = inventoryController.actualizarInventario(1, inventory);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void DeleteInventarioTest() {
        doNothing().when(inventoryService).deleteProduct(1);

        ResponseEntity<?> response = inventoryController.eliminarInventario(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteInventarioNoEncontradoTest() {
        doThrow(NoSuchElementException.class).when(inventoryService).deleteProduct(1);

        ResponseEntity<?> response = inventoryController.eliminarInventario(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteInventarioErrorTest() {
        doThrow(RuntimeException.class).when(inventoryService).deleteProduct(1);

        ResponseEntity<?> response = inventoryController.eliminarInventario(1);
        assertEquals(500, response.getStatusCodeValue());
    }
}