package grupo2.proyecto.full.stack1.Repository;


import grupo2.proyecto.full.stack1.Modelo.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface sucursalRepository extends JpaRepository<Sucursal, Integer> {

}
