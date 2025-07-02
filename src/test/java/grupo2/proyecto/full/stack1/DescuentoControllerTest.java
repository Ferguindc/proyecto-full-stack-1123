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

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        d = new descuento();
        d.setId(1);
    }

    // --- GET ALL ---
    @Test
    void GetAllDescuentosTest() {
        when(descuentoService.findAll()).thenReturn(List.of(d));
        when(assembler.toModel(d)).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.listarDescuentos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllDescuentosSinDescuentoTest() {
        when(descuentoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = descuentoController.listarDescuentos();
        assertEquals(404, response.getStatusCodeValue());
    }



    @Test
    void GetDescuentoByIdTest() {
        when(descuentoService.findById(1)).thenReturn(d);
        when(assembler.toModel(d)).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.obtenerDescuento(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetDescuentoByIdSinDescuentoTest() {
        when(descuentoService.findById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = descuentoController.obtenerDescuento(99);
        assertEquals(404, response.getStatusCodeValue());
    }



    @Test
    void PostDescuentoTest() {
        when(descuentoService.save(d)).thenReturn(d);
        when(assembler.toModel(d)).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.crearDescuento(d);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostDescuentosSintaxisInvalidaTest() {
        when(descuentoService.save(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = descuentoController.crearDescuento(new descuento());
        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    void PutDescuentoTest() {
        when(descuentoService.findById(1)).thenReturn(d);
        when(descuentoService.save(any())).thenReturn(d);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(d));

        ResponseEntity<?> response = descuentoController.actualizarDescuento(1, d);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutDescuentosSinDescuentoTest() {
        when(descuentoService.findById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = descuentoController.actualizarDescuento(1, d);
        assertEquals(404, response.getStatusCodeValue());
    }

 // no tengo idea pq tira error ahora esta wea si antes no me tiraba error dasbd

    @Test
    void DeleteDescuentoTest() {
        lenient().when(descuentoService.findById(1)).thenReturn(d);
        lenient().doNothing().when(descuentoService).delete(1);
        // era porq me faltaba el lenient...

        ResponseEntity<?> response = descuentoController.eliminarDescuento(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteDescuentosSinDescuentoTest() {
        doThrow(NoSuchElementException.class).when(descuentoService).delete(1);

        ResponseEntity<?> response = descuentoController.eliminarDescuento(1);
        assertEquals(404, response.getStatusCodeValue());
    }


}
