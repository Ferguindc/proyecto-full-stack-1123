package grupo2.proyecto.full.stack1.Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class  User {
    private int id;
    private String username;
    private String password;
    private String email;

}
