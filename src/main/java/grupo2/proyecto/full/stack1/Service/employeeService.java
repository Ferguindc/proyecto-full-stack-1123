package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Employee;
import grupo2.proyecto.full.stack1.Repository.employeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class employeeService {

    @Autowired
    private employeeRepository employeeRepository;

    public String addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Empleado agregado correctamente";
    }

    public String getAllEmployee() {
        String output = "";
        for (Employee employee : employeeRepository.findAll()) {
            output += "ID: " + employee.getId() + "\n";
            output += "Nombre del Empleado: " + employee.getNombre() + " " + employee.getApellido() + "\n";
            output += "Codigo postal: " + employee.getZipcode() + "\n";
            output += "Email: " + employee.getEmail() + "\n";
            output += "Telefono: " + employee.getTelefono() + "\n";

        }
        if (output.isEmpty()) {
            return "De momento no hay empleados.";
        } else {
            return output;
        }
    }


    public String getEmployeeById(int id) {
        String output ="";
        if(employeeRepository.existsById(id)){
            Employee employee = employeeRepository.findById(id).get();
            output += "ID: " + employee.getId() + "\n";
            output += "Nombre del Empleado: " + employee.getNombre() + " " + employee.getApellido() + "\n";
            output += "Codigo postal: " + employee.getZipcode() + "\n";
            output += "Email: " + employee.getEmail() + "\n";
            output += "Telefono: " + employee.getTelefono() + "\n";
            return output;
        }else{
            return "No existe un trabajador con el id ingresado.";
        }
    }

    public String deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Empleado eliminado correctamente";
        } else {
            return "No existe un trabajador con el id ingresado.";
        }
    }

    public String updateEmployee(int id, Employee employee) {
        if (employeeRepository.existsById(id)) {
            Employee buscado = employeeRepository.findById(id).get();
            buscado.setNombre(employee.getNombre());
            buscado.setApellido(employee.getApellido());
            buscado.setZipcode(employee.getZipcode());
            buscado.setEmail(employee.getEmail());
            buscado.setTelefono(employee.getTelefono());
            employeeRepository.save(buscado);
            return "Empleado actualizado correctamente.";
        } else {
            return "No existe un trabajador con el id ingresado.";
        }
    }
}
