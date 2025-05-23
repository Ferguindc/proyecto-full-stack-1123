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
        @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Inventory inventario;
        @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private proveedor proveedor;
        @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;


}
