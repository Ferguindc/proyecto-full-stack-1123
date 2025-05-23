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
public class pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int precioPedido;
    private String metodoPago;
    private int costoEnvio;
    private LocalDate fechaEnvio;
    private int numSerie;
    private String metodoEnvio;
        @ManyToOne
    @JoinColumn(name = "descuento_id", nullable = false)
    private descuento descuento;
        @ManyToOne
    @JoinColumn(name = "envio_id", nullable = false)
    private envio envio;
        @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;



}
