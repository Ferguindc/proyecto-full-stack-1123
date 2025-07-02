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

    @BeforeEach
    void setUp() {
        cargoEjemplo = new Cargo();
        cargoEjemplo.setId(1);
        cargoEjemplo.setNombre("Desarrollador");
        cargoEjemplo.setSalario(1000000);
    }

    // ----------- GET ALL -----------

    @Test
    void GetAllCargosTest() {
        when(cargoService.findAll()).thenReturn(List.of(cargoEjemplo));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.listarCargos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllCargos_EmptyList_Returns404() {
        when(cargoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = cargoController.listarCargos();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetAllCargos_MultipleResults() {
        Cargo otroCargo = new Cargo();
        otroCargo.setId(2);
        otroCargo.setNombre("QA");
        otroCargo.setSalario(900000);

        when(cargoService.findAll()).thenReturn(List.of(cargoEjemplo, otroCargo));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.listarCargos();
        assertEquals(200, response.getStatusCodeValue());
    }


    // ----------- GET BY ID -----------

    @Test
    void GetCargoByIdTest() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.obtenerCargo(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetCargoById_NotFound_Returns404() {
        when(cargoService.findById(99)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = cargoController.obtenerCargo(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetCargoById_ReturnsCorrectObject() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.obtenerCargo(1);
        EntityModel<?> result = (EntityModel<?>) response.getBody();
        assertNotNull(result);
    }


    // ----------- POST -----------

    @Test
    void PostCargoTest() {
        when(cargoService.save(any())).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.crearCargo(cargoEjemplo);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostCargo_InvalidData_Returns400() {
        when(cargoService.save(any())).thenThrow(new RuntimeException());

        ResponseEntity<?> response = cargoController.crearCargo(new Cargo());
        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    void PostCargo_ReturnsAssemblerResult() {
        when(cargoService.save(any())).thenReturn(cargoEjemplo);
        EntityModel<Cargo> model = EntityModel.of(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(model);

        ResponseEntity<?> response = cargoController.crearCargo(cargoEjemplo);
        assertSame(model, response.getBody());
    }

    // ----------- PUT -----------

    @Test
    void PutCargoTest() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(cargoService.save(any())).thenReturn(cargoEjemplo);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(cargoEjemplo));

        ResponseEntity<?> response = cargoController.actualizarCargo(1, cargoEjemplo);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void PutCargo_NotFound_Returns404() {
        when(cargoService.findById(1)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = cargoController.actualizarCargo(1, cargoEjemplo);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PutCargo_ThrowsException_Returns400() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(cargoService.save(any())).thenThrow(new RuntimeException());

        ResponseEntity<?> response = cargoController.actualizarCargo(1, cargoEjemplo);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutCargo_UpdatesValuesCorrectly() {
        Cargo entrada = new Cargo();
        entrada.setNombre("Tester");
        entrada.setSalario(800000);

        Cargo actualizado = new Cargo();
        actualizado.setNombre("Tester");
        actualizado.setSalario(800000);

        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        when(cargoService.save(any())).thenReturn(actualizado);
        when(assembler.toModel(actualizado)).thenReturn(EntityModel.of(actualizado));

        ResponseEntity<?> response = cargoController.actualizarCargo(1, entrada);
        assertEquals(200, response.getStatusCodeValue());
    }

    // ----------- DELETE -----------

    @Test
    void DeleteCargoTest() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        doNothing().when(cargoService).delete(1);

        ResponseEntity<?> response = cargoController.eliminarCargo(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteCargo_NotFound_Returns404() {
        when(cargoService.findById(99)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = cargoController.eliminarCargo(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteCargo_ThrowsException_Returns500() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        doThrow(new RuntimeException()).when(cargoService).delete(1);

        ResponseEntity<?> response = cargoController.eliminarCargo(1);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void DeleteCargo_VerifyServiceCalled() {
        when(cargoService.findById(1)).thenReturn(cargoEjemplo);
        doNothing().when(cargoService).delete(1);

        cargoController.eliminarCargo(1);
        verify(cargoService).delete(1);
    }
}
