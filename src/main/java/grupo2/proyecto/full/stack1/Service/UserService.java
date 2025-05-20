package grupo2.proyecto.full.stack1.Service;

import grupo2.proyecto.full.stack1.Modelo.User;
import grupo2.proyecto.full.stack1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(User user) {
        userRepository.save(user);
        return "Usuario agregado correctamente";
    }

    public String getAllUser() {
        String output = "";
        for (User user : userRepository.findAll()) {
            output += "ID: " + user.getId() + "\n";
            output += "Nombre: " + user.getUsername() + "\n";
            output += "Contraseña: " + user.getPassword() + "\n\n";
            output += "Email: " + user.getEmail() + "\n\n";
        }
        if (output.isEmpty()) {
            return "Todavía no se ha agregado ningun usuario.";
        } else {
            return output;
        }
    }

    public String getUserById(int id) {
        String output="";
        if(userRepository.existsById(id)){
            User user = userRepository.findById(id).get();
            output += "ID: " + user.getId() + "\n";
            output += "Nombre: " + user.getUsername() + "\n";
            output += "Contraseña: " + user.getPassword() + "\n\n";
            output += "Email: " + user.getEmail() + "\n\n";
            return output;
        }else{
            return "No se ha encontrado el usuario";
        }
    }

    public String deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "Usuario eliminado correctamente";
        } else {
            return "No se ha encontrado el usuario.";
        }
    }

    public String updateUser(int id, User user) {
        if (userRepository.existsById(id)) {
            User buscado = userRepository.findById(id).get();
            buscado.setId(user.getId());
            buscado.setUsername(user.getUsername());
            buscado.setPassword(user.getPassword());
            buscado.setEmail(user.getEmail());
            userRepository.save(buscado);
            return "Usuario actualizado correctamente.";
        } else {
            return "No se ha encontrado el usuario.";
        }
    }
}