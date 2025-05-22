package grupo2.proyecto.full.stack1.Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class reStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int stock;
    private LocalDate fechaReStock;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Inventory inventario;
    @OneToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private proveedor proveedor;
    @OneToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;


}
