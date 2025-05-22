package grupo2.proyecto.full.stack1.Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String usuario;
    private String password;
    @Column(nullable = false)
    private String nombre;
    private String apellido;
    private String email;
}
