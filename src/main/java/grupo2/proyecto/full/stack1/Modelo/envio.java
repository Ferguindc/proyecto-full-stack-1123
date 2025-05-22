package grupo2.proyecto.full.stack1.Modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class envio {
    @Id
    @GeneratedValue
    private Long id;
    private Long codigoEnvio;
    private Long numeroEnvio;
    private String direccionEnvio;

}
