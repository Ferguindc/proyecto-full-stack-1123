package Modelo;

public class inventario {
    Private int cod_id;
    Private String componente;
    Private int valorUnitario;
    Private int Stock;
    Private String nombre;

    public inventario() {
    }

    public inventario(int cod_id, Private string, int valorUnitario, int stock, Private string1) {
        this.cod_id = cod_id;
        String = string;
        this.valorUnitario = valorUnitario;
        Stock = stock;
        String = string1;
    }

    public int getCod_id() {
        return cod_id;
    }

    public void setCod_id(int cod_id) {
        this.cod_id = cod_id;
    }

    public Private getString() {
        return String;
    }

    public void setString(Private string) {
        String = string;
    }

    public int getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(int valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }
}
