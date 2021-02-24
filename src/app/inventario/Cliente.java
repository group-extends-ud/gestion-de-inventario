package app.inventario;

public class Cliente {

    private String idcliente, nombre, apellido;

    public Cliente(String idcliente, String nombre, String apellido) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getIdcliente() {
        return idcliente;
    }
    public void setId(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
