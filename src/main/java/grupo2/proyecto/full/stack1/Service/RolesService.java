package grupo2.proyecto.full.stack1.Service;


import grupo2.proyecto.full.stack1.Modelo.Roles;
import grupo2.proyecto.full.stack1.Repository.RolesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RolesService {

    private RolesRepository rolesRepository;

    public List<Roles> findAll() {
       return rolesRepository.findAll();
    }
    public Roles findById(int id) {
        return rolesRepository.findById(id).get();
    }
    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }
    public void delete(int id) {
        rolesRepository.deleteById(id);
    }

}
