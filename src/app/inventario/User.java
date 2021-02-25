package app.inventario;

public class User {
     private String idcliente, nombre, apellido;
     private boolean isAdmin;

    public User(String idcliente, String nombre, String apellido, boolean Admin) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.isAdmin = Admin;
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

    public boolean isIsAdmin() {
        return isAdmin;
    }

}
