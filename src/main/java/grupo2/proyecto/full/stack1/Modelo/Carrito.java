package grupo2.proyecto.full.stack1.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private int cantidad;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}

