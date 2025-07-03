package grupo2.proyecto.full.stack1;
import grupo2.proyecto.full.stack1.Controller.employeeController;
import grupo2.proyecto.full.stack1.Modelo.Employee;
import grupo2.proyecto.full.stack1.Service.employeeService;

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
class employeeControllerTest {


    @InjectMocks
    private employeeController employeeController;

    @Mock
    private employeeService employeeService;

    private Employee employee;

    // EL BEFOREEACH SIRVE PARA SIMULAR QUE EXISTAN DATOS, YA QUE ESTABA TENIENDO PROBLEMAS CON LA BASE DE DATOS.
    @BeforeEach
    void setup() {
        employee = new Employee();
        employee.setId(1);
        employee.setNombre("Carlos");
        employee.setApellido("Gomez");
        employee.setZipcode(1321312);
        employee.setEmail("carlos.gomez@example.com");
        employee.setTelefono(231231231);
    }

    @Test
    void GetAllEmployeesTest() {
        when(employeeService.getAllEmployee()).thenReturn(List.of(employee));

        ResponseEntity<?> response = employeeController.getAllEmployee();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetAllEmployeesSinEmpleadosTest() {
        when(employeeService.getAllEmployee()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = employeeController.getAllEmployee();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void GetEmployeeByIdTest() {
        when(employeeService.getEmployeeById(1)).thenReturn(employee);

        ResponseEntity<?> response = employeeController.getEmployeeById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void GetEmployeeByIdSinEmpleadoTest() {
        when(employeeService.getEmployeeById(99)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = employeeController.getEmployeeById(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void PostEmployeeTest() {
        when(employeeService.addEmployee(employee)).thenReturn(employee);

        ResponseEntity<?> response = employeeController.addEmployee(employee);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void PostEmployeeDatosInvalidosTest() {
        when(employeeService.addEmployee(any())).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = employeeController.addEmployee(new Employee());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void PutEmployeeTest() {
        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        when(employeeService.addEmployee(any())).thenReturn(employee);

        ResponseEntity<?> response = employeeController.actualizarEmpleado(1, employee);
        assertEquals(200, response.getStatusCodeValue());
    }


    //Este test tira error porque al no tener conectada la base de datos no hay valores para q pueda hacer la validación.
    @Test
    void PutEmployeeSinEmpleadoTest() {
        when(employeeService.getEmployeeById(1)).thenThrow(NoSuchElementException.class);

        ResponseEntity<?> response = employeeController.actualizarEmpleado(1, employee);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void DeleteEmployeeTest() {
        doNothing().when(employeeService).deleteEmployee(1);

        ResponseEntity<?> response = employeeController.deleteEmployee(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void DeleteEmployeeSinEmpleadoTest() {
        doThrow(NoSuchElementException.class).when(employeeService).deleteEmployee(1);

        ResponseEntity<?> response = employeeController.deleteEmployee(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}