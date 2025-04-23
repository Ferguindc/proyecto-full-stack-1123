package Modelo;

import org.springframework.stereotype.Service;





public class User {
    private String Username;
    private String password;
    private String email;


    public User() {

    }

    public User(String username, String password, String email) {
        Username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        String output="";
        output+= "Nombre de Usuario: "+username+"\n";
        output+= "Contraseña: "+password+"\n";
        output+= "Correo: "+email+"\n";
        return output;
    }

}