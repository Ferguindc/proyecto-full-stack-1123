package grupo2.proyecto.full.stack1;

import grupo2.proyecto.full.stack1.Controller.CargoController;
import grupo2.proyecto.full.stack1.Modelo.Cargo;
import grupo2.proyecto.full.stack1.Service.cargoService;
import grupo2.proyecto.full.stack1.Assembler.CargoModelAssembler;

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
class CargoControllerTest {

    @InjectMocks
    private CargoController cargoController;

    @Mock
    private cargoService cargoService;

    @Mock
    private CargoModelAssembler assembler;

    private Cargo cargoEjemplo;


    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setUp() {
        cargoEjemplo = new Cargo();
        cargoEjemplo.setId(1);
        cargoEjemplo.setNombre("Desarrollador");
        cargoEjemplo.setSalario(1000000);
    }


    @Test
    void GetAllCargosTest() {
        when(cargoService.findAll()).thenReturn(List.of(cargoEjemplo));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.listarCargos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllCargosSinCargosTest() {
        when(cargoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = cargoController.listarCargos();
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void GetCargoByIdTest() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.obtenerCargo(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetCargoByIdSinCargosTest() {
        when(cargoService.findById(99)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = cargoController.obtenerCargo(99);
        assertEquals(404, response.getStatusCodeValue());
    }



    @Test
    void PostCargoTest() {
        when(cargoService.save(any())).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.crearCargo(cargoEjemplo);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostCargoSintaxisInvalidaTest() {
        when(cargoService.save(any())).thenThrow(new RuntimeException());

        ResponseEntity<?> response = cargoController.crearCargo(new Cargo());
        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    void PutCargoTest() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(cargoService.save(any())).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.actualizarCargo(1, cargoEjemplo);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutCargoSinCargosTest() {
        when(cargoService.findById(1)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = cargoController.actualizarCargo(1, cargoEjemplo);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteCargoTest() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        doNothing().when(cargoService).delete(1);

        ResponseEntity<?> response = cargoController.eliminarCargo(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteCargoSinCargosTest() {
        when(cargoService.findById(99)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = cargoController.eliminarCargo(99);
        assertEquals(404, response.getStatusCodeValue());
    }

}
