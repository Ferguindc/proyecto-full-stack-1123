package grupo2.proyecto.full.stack1.Repository;

import grupo2.proyecto.full.stack1.Modelo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface adminRepository extends JpaRepository<Admin, Integer> {
}
