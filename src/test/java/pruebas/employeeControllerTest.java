package pruebas;

import grupo2.proyecto.full.stack1.Controller.employeeController;
import grupo2.proyecto.full.stack1.Modelo.Employee;
import grupo2.proyecto.full.stack1.Service.employeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class employeeControllerTest {
        @InjectMocks
        private employeeController employeeController;

        @Mock
        private employeeService employeeService;

        private Employee empleado;

        @BeforeEach
        void setup() {
            empleado = new Employee();
            empleado.setId(1);
            empleado.setNombre("Pedro");
        }

        @Test
        void getAllEmployee_OK() {
            when(employeeService.getAllEmployee()).thenReturn(List.of(empleado));

            ResponseEntity<?> response = employeeController.getAllEmployee();
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void getAllEmployee_EmptyList() {
            when(employeeService.getAllEmployee()).thenReturn(Collections.emptyList());

            ResponseEntity<?> response = employeeController.getAllEmployee();
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void getAllEmployee_NullList() {
            when(employeeService.getAllEmployee()).thenReturn(null);

            ResponseEntity<?> response = employeeController.getAllEmployee();
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void getAllEmployee_MultipleResults() {
            when(employeeService.getAllEmployee()).thenReturn(List.of(empleado, new Employee()));

            ResponseEntity<?> response = employeeController.getAllEmployee();
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void getEmployeeById_OK() {
            when(employeeService.getEmployeeById(1)).thenReturn(empleado);

            ResponseEntity<?> response = employeeController.getEmployeeById(1);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void getEmployeeById_NotFound() {
            when(employeeService.getEmployeeById(99)).thenThrow(NoSuchElementException.class);

            ResponseEntity<?> response = employeeController.getEmployeeById(99);
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void getEmployeeById_InvalidId() {
            ResponseEntity<?> response = employeeController.getEmployeeById(-1);
            assertTrue(response.getStatusCode().is4xxClientError());
        }

        @Test
        void getEmployeeById_ReturnsCorrectEmployee() {
            when(employeeService.getEmployeeById(1)).thenReturn(empleado);

            ResponseEntity<?> response = employeeController.getEmployeeById(1);
            assertEquals(empleado, response.getBody());
        }

        @Test
        void addEmployee_OK() {
            when(employeeService.addEmployee(empleado)).thenReturn(empleado);

            ResponseEntity<?> response = employeeController.addEmployee(empleado);
            assertEquals(201, response.getStatusCodeValue());
        }

        @Test
        void addEmployee_BadRequest() {
            when(employeeService.addEmployee(any())).thenThrow(RuntimeException.class);

            ResponseEntity<?> response = employeeController.addEmployee(new Employee());
            assertEquals(400, response.getStatusCodeValue());
        }

        @Test
        void addEmployee_NullInput() {
            ResponseEntity<?> response = employeeController.addEmployee(null);
            assertEquals(400, response.getStatusCodeValue());
        }

        @Test
        void addEmployee_ReturnsSaved() {
            when(employeeService.addEmployee(any())).thenReturn(empleado);

            ResponseEntity<?> response = employeeController.addEmployee(empleado);
            assertEquals(empleado, response.getBody());
        }

        @Test
        void actualizarEmpleado_OK() {
            when(employeeService.getEmployeeById(1)).thenReturn(empleado);
            when(employeeService.addEmployee(any())).thenReturn(empleado);

            ResponseEntity<?> response = employeeController.actualizarEmpleado(1, empleado);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void actualizarEmpleado_NotFound() {
            when(employeeService.getEmployeeById(1)).thenThrow(NoSuchElementException.class);

            ResponseEntity<?> response = employeeController.actualizarEmpleado(1, empleado);
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void actualizarEmpleado_Exception() {
            when(employeeService.getEmployeeById(1)).thenReturn(empleado);
            when(employeeService.addEmployee(any())).thenThrow(RuntimeException.class);

            ResponseEntity<?> response = employeeController.actualizarEmpleado(1, empleado);
            assertEquals(400, response.getStatusCodeValue());
        }

        @Test
        void actualizarEmpleado_DataChanged() {
            Employee actualizado = new Employee();
            actualizado.setNombre("Luis");

            when(employeeService.getEmployeeById(1)).thenReturn(empleado);
            when(employeeService.addEmployee(any())).thenReturn(actualizado);

            ResponseEntity<?> response = employeeController.actualizarEmpleado(1, actualizado);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void deleteEmployee_OK() {
            doNothing().when(employeeService).deleteEmployee(1);

            ResponseEntity<?> response = employeeController.deleteEmployee(1);
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void deleteEmployee_NotFound() {
            doThrow(NoSuchElementException.class).when(employeeService).deleteEmployee(1);

            ResponseEntity<?> response = employeeController.deleteEmployee(1);
            assertEquals(404, response.getStatusCodeValue());
        }

        @Test
        void deleteEmployee_Exception() {
            doThrow(RuntimeException.class).when(employeeService).deleteEmployee(1);

            ResponseEntity<?> response = employeeController.deleteEmployee(1);
            assertEquals(500, response.getStatusCodeValue());
        }
        @Test
        void deleteEmployee_VerifyCall() {
            doNothing().when(employeeService).deleteEmployee(1);

            employeeController.deleteEmployee(1);
            verify(employeeService).deleteEmployee(1);
        }
}