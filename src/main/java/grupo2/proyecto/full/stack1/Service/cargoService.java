package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Cargo;
import grupo2.proyecto.full.stack1.Repository.cargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cargoService {

    @Autowired
    private cargoRepository cargoRepository;

    public String addCargo(Cargo cargo) {
        cargoRepository.save(cargo);
        return "Cargo agregado correctamente";
    }

    public String getAllCargo() {
        String output = "";
        for (Cargo cargo : cargoRepository.findAll()) {
            output += "ID: " + cargo.getId() + "\n";
            output += "Nombre del Cargo: " + cargo.getNombre() + "\n";
            output += "Salario: " + cargo.getSalario() + "\n\n";
        }
        if (output.isEmpty()) {
            return "Todavía no se ha agregado ningun cargo.";
        } else {
            return output;
        }
    }

    public String getCargoById(int id) {
        String output="";
        if(cargoRepository.existsById(id)){
            Cargo cargo = cargoRepository.findById(id).get();
            output+="ID Cargo: "+cargo.getId()+"\n";
            output+="Nombre: "+cargo.getNombre()+"\n";
            output+="Salario: "+cargo.getSalario()+"\n";
            return output;
        }else{
            return "No se ha encontrado el cargo";
        }
    }

    public String deleteCargo(int id) {
        if (cargoRepository.existsById(id)) {
            cargoRepository.deleteById(id);
            return "Cargo eliminado correctamente";
        } else {
            return "No se ha encontrado el cargo.";
        }
    }

    public String updateCargo(int id, Cargo cargo) {
        if (cargoRepository.existsById(id)) {
            Cargo buscado = cargoRepository.findById(id).get();
            buscado.setNombre(cargo.getNombre());
            buscado.setSalario(cargo.getSalario());
            cargoRepository.save(buscado);
            return "Cargo actualizado correctamente.";
        } else {
            return "No se ha encontrado el cargo.";
        }
    }
}
