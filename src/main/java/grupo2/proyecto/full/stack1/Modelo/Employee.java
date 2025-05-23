package grupo2.proyecto.full.stack1.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nombre;
    private String apellido;
    private int zipcode;
    private String email;
    private int telefono;
        @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;
        @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;
}
