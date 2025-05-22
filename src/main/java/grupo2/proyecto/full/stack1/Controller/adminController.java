package grupo2.proyecto.full.stack1.Controller;

import grupo2.proyecto.full.stack1.Modelo.Admin;
import grupo2.proyecto.full.stack1.Service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private adminService adminService;

    @GetMapping
    public String getAllAdmin() {
        return adminService.getAllAdmin();
    }

    @GetMapping("/{id}")
    public String getAdminById(@PathVariable int id) {
        return adminService.getAdminById(id);
    }

    @PostMapping()
    public String addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable int id) {
        return adminService.deleteAdmin(id);
    }

    @PutMapping("/{id}")
    public String updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

}
