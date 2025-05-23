package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Employee;
import grupo2.proyecto.full.stack1.Repository.employeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class employeeService {

    @Autowired
    private employeeRepository employeeRepository;

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).get();
    }

    public Employee addEmployee(Employee empleado) {
        return employeeRepository.save(empleado);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

}
