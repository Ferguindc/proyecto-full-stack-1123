package grupo2.proyecto.full.stack1.Assembler;

import grupo2.proyecto.full.stack1.Controller.employeeController;
import grupo2.proyecto.full.stack1.Modelo.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(employeeController.class).getEmployeeById(empleado.getId())).withSelfRel(),
                linkTo(methodOn(employeeController.class).getAllEmployee()).withRel("todosLosEmpleados"));
    }
}
