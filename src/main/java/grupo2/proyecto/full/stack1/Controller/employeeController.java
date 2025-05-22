package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Employee;
import grupo2.proyecto.full.stack1.Service.employeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleado")

public class employeeController {


    @Autowired
    private employeeService EmployeeService;

    @GetMapping
    public String getAllEmployee() {
        return EmployeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable int id) {
        return EmployeeService.getEmployeeById(id);
    }

    @PostMapping()
    public String addEmployee(@RequestBody Employee employee) {
        return EmployeeService.addEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return EmployeeService.deleteEmployee(id);
    }

    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        return EmployeeService.updateEmployee(id, employee);
    }
}
