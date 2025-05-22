package grupo2.proyecto.full.stack1.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class proveedor {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Column(nullable = false)
   private String nombreProveedor;
   private String direccionProveedor;
   private String telefonoProveedor;
   private String emailProveedor;

}
