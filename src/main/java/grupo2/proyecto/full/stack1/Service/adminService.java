package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.Admin;
import grupo2.proyecto.full.stack1.Repository.adminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminService {

    @Autowired
    private adminRepository adminRepository;

    public String addAdmin(Admin admin){
        adminRepository.save(admin);
        return "Administrador agregado exitosamente.";
    }
    public String getAllAdmin(){
        String output = "";
        for(Admin admin : adminRepository.findAll()){
            output += "ID: " + admin.getId() + "\n";
            output += "Usuario: " + admin.getNombre() + "\n";
            output += "Password: " + admin.getPassword() + "\n";
            output += "Nombre: " + admin.getNombre() + "\n";
            output += "Apellido: " + admin.getApellido() + "\n";
            output += "Email: " + admin.getEmail() + "\n";
        } if (output.isEmpty()) {
            return "No hay administradores.";
        } else {
            return output;
        }
    }

    public String getAdminById(int id){
        String output = "";
        if (adminRepository.existsById(id)) {
            Admin admin = adminRepository.findById(id).get();
            output += "ID: " + admin.getId() + "\n";
            output += "Usuario: " + admin.getNombre() + "\n";
            output += "Password: " + admin.getPassword() + "\n";
            output += "Nombre: " + admin.getNombre() + "\n";
            output += "Apellido: " + admin.getApellido() + "\n";
            output += "Email: " + admin.getEmail() + "\n";
            return output;
        } else {
            return "No existe el administrador.";
        }
    }

    public String deleteAdmin(int id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return "Administrador eliminado.";
        } else {
            return "No existe el administrador.";
        }
    }

    public String updateAdmin(int id, Admin admin) {
        if (adminRepository.existsById(id)) {
            Admin buscado = adminRepository.findById(id).get();
            buscado.setNombre(admin.getNombre());
            buscado.setApellido(admin.getApellido());
            buscado.setEmail(admin.getEmail());
            adminRepository.save(buscado);
            return "Administrador actualizado.";
        }else{
            return "No existe el administrador.";
        }
    }
}
