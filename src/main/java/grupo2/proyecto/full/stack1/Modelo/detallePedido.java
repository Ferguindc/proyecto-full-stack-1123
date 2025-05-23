package grupo2.proyecto.full.stack1.Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class detallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cantidadProductos;
    private int precioUnitario;
    private Long precioTotal;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private pedido pedido;

    @OneToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;


}
