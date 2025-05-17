package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Cargo;
import grupo2.proyecto.full.stack1.Service.cargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargos")
public class cargoController {

    @Autowired
    private cargoService CargoService;
    @Autowired
    private cargoService cargoService;

    @GetMapping
    public String getAllCargo() {
        return CargoService.getAllCargo();
    }

    @GetMapping("/{id}")
    public String getCargoById(@PathVariable int id) {
        return cargoService.getCargoById(id);
    }

    @PostMapping()
    public String addCargo(@RequestBody Cargo cargo) {
        return CargoService.addCargo(cargo);
    }

    @DeleteMapping("/{id}")
    public String deleteCargo(@PathVariable int id) {
        return CargoService.deleteCargo(id);
    }

    @PutMapping("/{id}")
    public String updateCargo(@PathVariable int id, @RequestBody Cargo cargo) {
        return CargoService.updateCargo(id, cargo);
    }
}
