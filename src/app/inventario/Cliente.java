package app.inventario;

public class Cliente extends General {

    private Integer idcliente;
    private String nombre, apellido;

    public Cliente(Integer idcliente, String nombre, String apellido) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Integer getIdcliente() {
        return idcliente;
    }
    public void setId(Integer idcliente) {
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

    public static String[] toArrayAtributes() {

        return toArrayAtributes(Cliente.class.getDeclaredFields());

    }

    @Override
    public Object[] toArray() {

        Object[] objects = { idcliente, nombre, apellido };

        return objects;

    }
    

}
