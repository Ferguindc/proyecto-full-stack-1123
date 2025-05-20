package grupo2.proyecto.full.stack1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import grupo2.proyecto.full.stack1.Modelo.Admin;


@Repository
public interface adminRepository extends JpaRepository<Admin, Integer> {
}
